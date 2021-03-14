package xyz.jereznx.spring.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import xyz.jereznx.spring.redis.mq.RedisMqClient;

import java.util.Set;
import java.util.concurrent.*;

@Slf4j
@SpringBootTest
class SpringRedisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    //    StringRedisTemplate
    @Autowired
    private RedisMqClient redisMqClient;

    @Autowired
    private ApplicationContext context;

    //    @Autowired
    private String test;


    public String getTest() {
        return context.getBean("test", String.class);
    }

    @Test
    void contextLoads() {
        final Set keys = redisTemplate.keys("*");
        final ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("spring", "nb");
        System.out.println(valueOperations.get("spring"));
        System.out.println(keys);
    }

    @Test
    void testOpt() {
        Boolean res = redisTemplate.opsForValue().setIfAbsent("aa", "11", 20L, TimeUnit.SECONDS);
        System.out.println(res);
    }

    @Test
    void testMQ() {
        redisMqClient.publish("test", "233");
        while (true) {

        }
    }

    @Test
    void testPrototype() {
        Object test1 = context.getBean("test");
        Object test2 = context.getBean("test");
        System.out.println(test1 == test2);
        new Thread(() -> {
            System.out.println(getTest().hashCode());
        }).start();
        new Thread(() -> {
            System.out.println(getTest().hashCode());
        }).start();
    }

}
