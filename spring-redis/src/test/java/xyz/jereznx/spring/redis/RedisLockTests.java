package xyz.jereznx.spring.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import xyz.jereznx.spring.redis.distributed.RedisLock;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author liqilin
 * @since 2021/2/5 17:23
 */
@SpringBootTest
public class RedisLockTests {

    @Autowired
    private RedisLock redisLock;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisTemplate<String, Integer> intRedisTemplate;

    /**
     * 启动多个实例测试
     */
    @Test
    void test() throws InterruptedException {
        String countKey = "count";
        intRedisTemplate.opsForValue().setIfAbsent(countKey, 0);
        while (true) {
            final String key = "test";
            final String requestId = Thread.currentThread().getId() + String.valueOf(UUID.randomUUID());
            final boolean success = redisLock.lock(key, requestId);
            if (success) {
                intRedisTemplate.opsForValue().increment(countKey);
                System.out.println(intRedisTemplate.opsForValue().get(countKey));
                System.out.println(requestId);
                System.out.println(redisTemplate.opsForValue().get(key));
                System.out.println("----------------------------------");
                TimeUnit.SECONDS.sleep(2);
                redisLock.unlock(key, requestId);
            }
        }
    }

}
