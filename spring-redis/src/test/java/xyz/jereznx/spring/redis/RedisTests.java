package xyz.jereznx.spring.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author liqilin
 * @since 2020/12/1 15:09
 */
@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void expire() {
        redisTemplate.expire("lql1", 3, TimeUnit.SECONDS);
    }

    @Test
    void string() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = "t";
        System.out.println(valueOperations.increment(key));
    }

    @Test
    void list() {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        String key = "mylist";
        listOperations.leftPush(key, "1");
        listOperations.leftPush(key, "2");
//        redisTemplate.expire(key, 10, TimeUnit.SECONDS);
        List<String> list = listOperations.range(key, 0, -1);
        System.out.println(list);
        redisTemplate.delete(key);

//        redisTemplate.expire()
    }

    @Test
    void eval() {
//        get(key) = args ，调用del(key)成功返回1
//        get(key) ！= args 或del(key)失败，返回0
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        final List<String> keys = Arrays.asList("test");
        Object args = "a";
        final RedisScript<Long> script1 = new RedisScript<Long>() {

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
                return script;
            }

            @Override
            public boolean returnsRawValue() {
                return true;
            }
        };
        final RedisSerializer<Long> redisSerializer = new RedisSerializer<Long>() {
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
        final Long res = redisTemplate.execute(script1, RedisSerializer.string(), redisSerializer, keys, args);
        System.out.println(res);
    }
}
