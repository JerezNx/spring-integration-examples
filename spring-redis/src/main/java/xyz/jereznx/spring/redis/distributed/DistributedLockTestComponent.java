package xyz.jereznx.spring.redis.distributed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author liqilin
 * @since 2021/2/6 11:08
 */
@Component
public class DistributedLockTestComponent {

    @Autowired
    private RedisTemplate<String, Integer> intRedisTemplate;

    @DistributedLock(key = "doSth")
    public void doSth() throws InterruptedException {
        String countKey = "lock_test_count";
        intRedisTemplate.opsForValue().setIfAbsent(countKey, 0);
        TimeUnit.SECONDS.sleep(2);
        intRedisTemplate.opsForValue().increment(countKey);
        System.out.println(intRedisTemplate.opsForValue().get(countKey));
        System.out.println("-------------------------------");
    }

}
