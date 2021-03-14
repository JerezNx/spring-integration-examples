package xyz.jereznx.spring.redis.distributed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * redis实现分布式 CountDownLatch
 *
 * @author liqilin
 * @since 2021/2/5 15:25
 */
@Slf4j
public class DistributedCountDownLatch {

    private static RedisTemplate<String, Integer> redisTemplate;

    private String countKey;

    private static final String KEY_PREFIX = "RedisCountDownLatch:";

    private static boolean inited;

    /**
     * @param key   有实际意义的多节点相同的key
     * @param count count
     */
    public DistributedCountDownLatch(String key, int count) {
        if (!inited) {
            throw new IllegalArgumentException("call method init once before use");
        }
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key is empty");
        }
        if (count < 0) {
            throw new IllegalArgumentException("count < 0");
        }
        countKey = KEY_PREFIX + key;
        redisTemplate.opsForValue().setIfAbsent(countKey, count);
    }

    /**
     * 阻塞当前线程，直到count 为0
     *
     * @throws InterruptedException e
     */
    public void await() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        while (true) {
            if (getCount() <= 0) {
                break;
            }
        }
        clear();
    }

    /**
     * 阻塞当前线程，直到count为0,或者超时
     *
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true:count为0正常结束；false:超时结束
     * @throws InterruptedException e
     */
    public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        final long startTime = System.currentTimeMillis();
        while (true) {
            if (getCount() <= 0) {
                clear();
                return true;
            }
            if ((System.currentTimeMillis() - startTime) >= unit.toMillis(timeout)) {
                clear();
                return false;
            }
        }
    }

    public void countDown() {
        redisTemplate.opsForValue().decrement(countKey);
    }

    /**
     * Returns the current count.
     *
     * <p>This method is typically used for debugging and testing purposes.
     *
     * @return the current count
     */
    public long getCount() {
        return Optional.ofNullable(redisTemplate.opsForValue().get(countKey)).orElse(0);
    }

    /**
     * 全局需且必需在使用该类前调用1次
     */
    public static void init(RedisTemplate<String, Integer> redisTemplate) {
        if (inited) {
            log.warn("RedisCountDownLatch has inited");
            return;
        }
        DistributedCountDownLatch.redisTemplate = redisTemplate;
        inited = true;
    }

    /**
     * Returns a string identifying this latch, as well as its state.
     * The state, in brackets, includes the String {@code "Count ="}
     * followed by the current count.
     *
     * @return a string identifying this latch, as well as its state
     */
    @Override
    public String toString() {
        return super.toString() + "[Count = " + getCount() + "]";
    }

    private void clear() {
        redisTemplate.delete(countKey);
    }

}
