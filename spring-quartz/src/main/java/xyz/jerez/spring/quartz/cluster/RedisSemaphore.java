package xyz.jerez.spring.quartz.cluster;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.impl.jdbcjobstore.Semaphore;
import xyz.jereznx.spring.redis.distributed.RedisLock;
import xyz.jereznx.spring.redis.util.SpringContextUtil;

import java.sql.Connection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 利用redis实现分布式锁
 * todo 不对，quartz需要的是 ，没有获取到锁就阻塞
 *
 * @author liqilin
 * @since 2021/3/2 11:02
 */
@Slf4j
@Setter
public class RedisSemaphore implements Semaphore {

    private static RedisLock redisLock = SpringContextUtil.getBean(RedisLock.class);

    private static ConcurrentHashMap<String, String> lockOwners = new ConcurrentHashMap<>();

    private static final int DEFAULT_LOCK_TIME_OUT = 5000;

    /**
     * 锁超时时间
     */
    private int lockTimeOut = DEFAULT_LOCK_TIME_OUT;

    @Override
    public boolean obtainLock(Connection conn, String lockName) {
        lockName = lockName.intern();
        final String lockOwner = UUID.randomUUID().toString();
        while (!redisLock.lock(lockName, lockOwner, lockTimeOut)){
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lockOwners.put(lockName, lockOwner);
        return true;
    }

    @Override
    public void releaseLock(String lockName) {
        lockName = lockName.intern();
        if (lockOwners.get(lockName) == null) {
            return;
        }
        final String lockOwner = lockOwners.remove(lockName);
        redisLock.unlock(lockName, lockOwner);
    }

    @Override
    public boolean requiresConnection() {
        return false;
    }
}
