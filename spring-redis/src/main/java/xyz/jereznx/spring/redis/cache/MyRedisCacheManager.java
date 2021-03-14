package xyz.jereznx.spring.redis.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.PropertyResolver;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.util.StringUtils;
import xyz.jereznx.spring.redis.util.SpringContextUtil;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义，可动态设置过期时间的缓存处理器
 * 通过在cacheNames后面 补 #过期时间 ，进行设置
 *
 * @author liqilin
 * @since 2021/3/4 11:34
 */
@Slf4j
public class MyRedisCacheManager extends RedisCacheManager {

    private static final String SPLIT_FLAG = "#";

    private static PropertyResolver propertyResolver = SpringContextUtil.getBean(PropertyResolver.class);

    private static ConcurrentHashMap<Long, RedisCacheConfiguration> cacheConfigCache = new ConcurrentHashMap<>();

    public MyRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        name = propertyResolver.resolvePlaceholders(name);
        if (StringUtils.isEmpty(name) || !name.contains(SPLIT_FLAG)) {
            return super.createRedisCache(name, cacheConfig);
        }
        String[] cacheArray = name.split(SPLIT_FLAG);
        final int minLen = 2;
        if (cacheArray.length < minLen) {
            return super.createRedisCache(name, cacheConfig);
        }
        if (cacheConfig != null) {
            long cacheAge;
            try {
                cacheAge = Long.parseLong(cacheArray[cacheArray.length - 1]);
            } catch (NumberFormatException e) {
                log.warn("解析redis 过期时间异常", e);
                return super.createRedisCache(name, cacheConfig);
            }
            name = name.substring(0, name.lastIndexOf(SPLIT_FLAG));
            RedisCacheConfiguration finalCacheConfig = cacheConfig;
//            点进entryTtl方法，发现其是重新new了一个RedisCacheConfiguration，所以不会影响其他的
//            为了避免每次都重新创建，可以将其缓存下来
            long finalCacheAge = cacheAge;
            cacheConfig = cacheConfigCache.computeIfAbsent(cacheAge, k -> finalCacheConfig.entryTtl(Duration.ofSeconds(finalCacheAge)));
        }
        return super.createRedisCache(name, cacheConfig);
    }

}
