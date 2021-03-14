package xyz.jereznx.spring.redis.distributed;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author liqilin
 * @since 2021/2/6 10:45
 */
@Component
@Aspect
@Slf4j
public class DistributedLockAspect {

    @Autowired
    private RedisLock redisLock;

    private static final String LOCK_PREFIX = "DistributedLock:";

    @Pointcut("@annotation(xyz.jereznx.spring.redis.distributed.DistributedLock)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object res = null;
        DistributedLock lock = ((MethodSignature) point.getSignature()).getMethod().getAnnotation(DistributedLock.class);
        String clientId = UUID.randomUUID().toString();
        final String key = LOCK_PREFIX + lock.key();
        final int timeout = lock.timeout() * 1000;
        final boolean success = redisLock.lock(key, clientId, timeout);
        if (success) {
            try {
                res = point.proceed();
            } catch (Throwable throwable) {
                if (lock.throwException()) {
                    throw throwable;
                } else {
                    log.error("", throwable);
                }
            } finally {
                redisLock.unlock(key, clientId);
            }
        }
        return res;
    }

}
