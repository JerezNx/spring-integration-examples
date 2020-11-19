package xyz.jereznx.spring.springredis.lock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author liqilin
 * @since 2020/9/29 16:56
 */
@Component
public class RedisUtil {

    private RedisTemplate redisTemplate;
    private RedisLock redisLock;

    @Autowired
    public RedisUtil(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.redisLock = new RedisLock();
    }

    /**
     * 写入多个元素
     *
     * @param key
     * @param map
     */
    public void putAll(final String key, Map<String, String> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 获取多个元素
     *
     * @param key
     * @param subKeys
     * @return
     */
    public List getAll(final String key, List<String> subKeys) {
        return redisTemplate.opsForHash().multiGet(key, subKeys);
    }

    /**
     * 根据前缀获取所有缓存值
     *
     * @param keyPrefix
     * @return
     */
    public List<String> getStringDataByPrefix(final String keyPrefix) {
        List<String> data = Lists.newArrayList();
        Set<String> keys = redisTemplate.keys(keyPrefix + "*");
        for (String key : keys) {
            data.add((String) redisTemplate.opsForValue().get(key));
        }
        return data;
    }

    /**
     * 根据指定前缀获取一组值（慎用，小数据量可以）
     *
     * @param keyPrefix
     * @return
     */
    public List getAllByPrefix(final String keyPrefix, List<String> subKeys) {
        List<List<Object>> data = Lists.newArrayList();
        Set<String> keys = redisTemplate.keys(keyPrefix + "*");
        for (String key : keys) {
            data.add(redisTemplate.opsForHash().multiGet(key, subKeys));
        }
        return data;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value instanceof String ? value : JSON.toJSONString(value, SerializerFeature.WriteMapNullValue));
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value instanceof String ? value : JSON.toJSONString(value, SerializerFeature.WriteMapNullValue));
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置超时时间
     */
    public void expire(Object key, long timeout) {
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(List<String> keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public long removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            return redisTemplate.delete(keys);
        }
        return 0;
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存并更新超时时间
     *
     * @param key
     * @param expireSenconds
     * @return
     */
    public Object getOriginal(final String key, long expireSenconds) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);

        if (result != null) {
            redisTemplate.expire(key, expireSenconds, TimeUnit.SECONDS);
        }

        return result;
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public String get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return String.valueOf(result);
    }

    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, JSON.toJSONString(value, SerializerFeature.WriteMapNullValue));
    }

    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public String hmGet(String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return String.valueOf(
                Optional.ofNullable(hash.get(key, hashKey)).orElse(""));
    }

    /**
     * Hash删除Key
     */
    public void hmRemove(final String key, Object hashKey) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.delete(key, hashKey);
    }

    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    public void lPush(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * 列表获取
     *
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> lRange(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * 判断值在不在list中
     *
     * @param k
     * @param v
     * @return
     */
    public Boolean lmember(String k, String v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        Long size = list.size(k);
        List<Object> datas = list.range(k, 0, size);
        for (Object obj : datas) {
            if (((String) obj).equals(v)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 移除列表元素
     *
     * @param k redis存储的key值
     * @param c 标识，查找方向，0表示移除所有匹配元素，负数表示移除方向从尾到头，正数表示移除方向从头到尾
     * @param v 匹配的元素
     * @return
     */
    public Long lrem(String k, long c, String v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.remove(k, c, v);
    }

    /**
     * 集合添加
     *
     * @param key
     * @param value
     */
    public void add(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key, Object value, double scoure) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, value, scoure);
    }

    /**
     * 向set集合中添加一个元素
     *
     * @param key
     * @param value
     */
    public void sadd(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 删除set集合中的一个元素
     *
     * @param key
     * @param value
     */
    public void srem(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.remove(key, value);
    }

    /**
     * 判断一个元素在不在set集合中
     *
     * @param key
     * @param value
     */
    public Boolean smembers(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.isMember(key, value);
    }

    /**
     * 有序集合获取
     *
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }

    /**
     * 模糊匹配key
     * 支持* ? []
     * *：匹配所有字符
     * ?：匹配单个字符
     * []：匹配括号内的某一个字符
     *
     * @param pattern
     * @return
     */
    public Set<Object> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 分布式锁-获取锁
     */
    public boolean lock(String key) {
        return redisLock.lock(key);
    }

    /**
     * 分布式锁-释放锁
     */
    public void release(String key) {
        redisLock.release(key);
    }

    /**
     * Redis锁实现
     */
    private class RedisLock {
        // 获取锁超时
        private static final long LOCK_TIMEOUT = 500L;
        // 锁过期时间
        private static final long LOCK_EXPIRE = 5000L;

        public boolean lock(String key) {
            Boolean success = redisTemplate.opsForValue().setIfAbsent(key, "", LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
            success = success != null & success;
            if (success) {
                redisTemplate.expire(key, LOCK_EXPIRE, TimeUnit.MILLISECONDS);
            }
            return success;
        }

        public void release(String key) {
            redisTemplate.delete(key);
        }

    }


}
