package xyz.jereznx.spring.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.jereznx.spring.redis.cache.CacheOperationDemo;

/**
 * @author liqilin
 * @since 2021/3/4 13:06
 */
@SpringBootTest
public class RedisCacheTests {

    @Autowired
    private CacheOperationDemo cacheOperation;

    @Test
    void test() {
        System.out.println(cacheOperation.getDefault(1));
        System.out.println(cacheOperation.getDefault(1));
    }

    @Test
    void test2() {
        System.out.println(cacheOperation.getDynamic(1));
        System.out.println(cacheOperation.getDynamic(1));
    }

    @Test
    void test3() {
        System.out.println(cacheOperation.getDynamic(1));
        System.out.println(cacheOperation.getDefault(1));
    }

    @Test
    void test4() {
        System.out.println(cacheOperation.getProperty(1));
    }

}
