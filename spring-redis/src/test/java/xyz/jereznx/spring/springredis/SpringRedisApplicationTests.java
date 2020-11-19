package xyz.jereznx.spring.springredis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import xyz.jereznx.spring.springredis.lock.RedisUtil;
import xyz.jereznx.spring.springredis.mq.RedisMqClient;

import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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

    private static final String ROOM_ID = "RID";
    private static final String ROOM_ID_LOCK = "RID_LOCK1";

    @Autowired
    private RedisUtil REDIS_UTIL;

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

    @Test
    void testLock() throws ExecutionException, InterruptedException {
        Future<Integer> lastRes = null;
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 100; i++) {
//            lastRes = executorService.submit(this::getRoomId);
            lastRes = executorService.submit(this::getRoomIdNew);
        }
        executorService.awaitTermination(30, TimeUnit.SECONDS);
        System.out.println(lastRes.get());

    }

    private int getRoomId() {
        try {
            if (REDIS_UTIL.lock(ROOM_ID_LOCK)) {
                String tmp = REDIS_UTIL.get(ROOM_ID);
                int roomId = tmp != null && !"null".equals(tmp) ? Integer.parseInt(tmp) : 1000;
                roomId = ++roomId > 9999 ? 1000 : roomId;
                REDIS_UTIL.set(ROOM_ID, roomId);
                log.info("{}", roomId);
                return roomId;
            }
        } finally {
            REDIS_UTIL.release(ROOM_ID_LOCK);
        }
        return -1;
    }

    private int getRoomIdNew() throws InterruptedException {
        AtomicInteger count = new AtomicInteger();
        while (true) {
            if (count.getAndIncrement() > 5) {
                break;
            }
            if (REDIS_UTIL.lock(ROOM_ID_LOCK)) {
                try {
                    String tmp = REDIS_UTIL.get(ROOM_ID);
                    int roomId = tmp != null && !"null".equals(tmp) ? Integer.parseInt(tmp) : 1000;
                    roomId = ++roomId > 9999 ? 1000 : roomId;
                    REDIS_UTIL.set(ROOM_ID, roomId);
                    log.info("{}", roomId);
                    return roomId;
                } finally {
                    REDIS_UTIL.release(ROOM_ID_LOCK);
                }
            }
            Thread.sleep(100);
            log.info("none");
        }

        return -1;
    }
}
