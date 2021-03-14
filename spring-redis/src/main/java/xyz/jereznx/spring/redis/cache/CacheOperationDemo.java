package xyz.jereznx.spring.redis.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * 缓存操作类demo
 *
 * @author liqilin
 * @since 2021/3/4 13:15
 */
@Slf4j
@Component
public class CacheOperationDemo {

    @Cacheable(cacheNames = "DEFAULT_VALUE", key = "#id")
    public String getDefault(int id) {
        log.info("get default value ,id:{}", id);
        return "default value：" + id;
    }

    /**
     * #是分割符，30及表示30秒过期
     */
    @Cacheable(cacheNames = "DYNAMIC_VALUE#30", key = "#id")
    public String getDynamic(int id) {
        log.info("get dynamic value ,id:{}", id);
        return "dynamic value ：" + id;
    }

    @Cacheable(cacheNames = "PROPERTY_VALUE#${redis.cache.timeout:20}", key = "#id")
    public String getProperty(int id) {
        log.info("get property value ,id:{}", id);
        return "property value ：" + id;
    }

}
