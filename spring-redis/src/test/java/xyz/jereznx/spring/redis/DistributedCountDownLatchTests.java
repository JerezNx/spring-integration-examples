package xyz.jereznx.spring.redis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import xyz.jereznx.spring.redis.distributed.DistributedCountDownLatch;

import java.util.concurrent.TimeUnit;

/**
 * @author liqilin
 * @since 2021/2/5 15:56
 */
@SpringBootTest
public class DistributedCountDownLatchTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @BeforeEach
    void init() {
        DistributedCountDownLatch.init(redisTemplate);
    }

    @Test
    void test() throws InterruptedException {
        final int count = 5;
        DistributedCountDownLatch countDownLatch = new DistributedCountDownLatch("test", count * 2);
        for (int i = 0; i < count; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(finalI * 2);
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        System.out.println(countDownLatch.await(8, TimeUnit.SECONDS));
        System.out.println(1);
    }

}
