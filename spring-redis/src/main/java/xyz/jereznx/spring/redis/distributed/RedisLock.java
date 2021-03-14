package xyz.jereznx.spring.redis.distributed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * redis实现分布式锁
 *
 * @author liqilin
 * @since 2021/2/5 15:28
 */
@Component
public class RedisLock {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final AtomicRedisDelHelper atomicRedisDelHelper = new AtomicRedisDelHelper();

    /**
     * 锁默认过期时间
     */
    private static final long DEFAULT_LOCK_EXPIRE = 5000L;

    /**
     * 获取锁
     *
     * @param key       锁的key
     * @param requestId 请求锁的客户端id
     * @return 是否获取到锁
     */
    public boolean lock(String key, String requestId) {
        return this.lock(key, requestId, DEFAULT_LOCK_EXPIRE);
    }

    /**
     * 获取锁
     *
     * @param key        锁的key
     * @param requestId  请求锁的客户端id
     * @param expireTime 锁过期时间
     * @return 是否获取到锁
     */
    public boolean lock(String key, String requestId, long expireTime) {
        Boolean success = redisTemplate.opsForValue().setIfAbsent(key, requestId, expireTime, TimeUnit.MILLISECONDS);
        return success != null && success;
    }

    /**
     * 释放锁
     *
     * @param key       锁的key
     * @param requestId 请求锁的客户端id，与lock时保持一致
     */
    public void unlock(String key, String requestId) {
        atomicRedisDelHelper.compareAndDel(key, requestId);
    }

    /**
     * redis 比较并删除 原子操作类
     */
    class AtomicRedisDelHelper {

        private static final String scriptStr = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        private final RedisScript<Long> script = new RedisScript<Long>() {

            /**
             * 用于缓存脚本，后续调用不会传输脚本内容了
             */
            @Override
            public String getSha1() {
                return "del_lock";
            }

            @Override
            public Class<Long> getResultType() {
                return Long.class;
            }

            @Override
            public String getScriptAsString() {
                return scriptStr;
            }

            @Override
            public boolean returnsRawValue() {
                return true;
            }
        };

        final RedisSerializer<Long> longRedisSerializer = new RedisSerializer<Long>() {
            @Override
            public byte[] serialize(Long aLong) throws SerializationException {
                return longToBytes(aLong);
            }

            @Override
            public Long deserialize(byte[] bytes) throws SerializationException {
                return bytesToLong(bytes);
            }

            public byte[] longToBytes(long number) {
                long temp = number;
                byte[] b = new byte[8];
                for (int i = 0; i < b.length; i++) {
                    b[i] = new Long(temp & 0xff).byteValue();
                    // 将最低位保存在最低位
                    temp = temp >> 8;
                    // 向右移8位
                }
                return b;
            }

            public long bytesToLong(byte[] b) {
                long s = 0;
                long s0 = b[0] & 0xff;// 最低位
                long s1 = b[1] & 0xff;
                long s2 = b[2] & 0xff;
                long s3 = b[3] & 0xff;
                long s4 = b[4] & 0xff;// 最低位
                long s5 = b[5] & 0xff;
                long s6 = b[6] & 0xff;
                long s7 = b[7] & 0xff;

                // s0不变
                s1 <<= 8;
                s2 <<= 16;
                s3 <<= 24;
                s4 <<= 8 * 4;
                s5 <<= 8 * 5;
                s6 <<= 8 * 6;
                s7 <<= 8 * 7;
                s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
                return s;
            }
        };

        /**
         * 若redis中key的值 等于 expect，就删除 key
         *
         * @param key    k
         * @param expect v
         * @return success
         */
        boolean compareAndDel(String key, String expect) {
            final Long res = redisTemplate.execute(script, RedisSerializer.string(), longRedisSerializer, Collections.singletonList(key), expect);
            return Optional.ofNullable(res).map(i -> i == 1).orElse(false);
        }
    }

}
