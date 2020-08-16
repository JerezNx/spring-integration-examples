package xyz.jereznx.spring.springredis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Set;

@SpringBootTest
class SpringRedisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        final Set keys = redisTemplate.keys("*");
        final ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("spring","nb");
        System.out.println(keys);
    }

}
