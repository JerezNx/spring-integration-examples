package xyz.jereznx.spring.redis.distributed;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分布式锁
 *
 * @author liqilin
 * @since 2021/2/6 10:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributedLock {

    /**
     * 锁key
     */
    String key();

    /**
     * 锁超时时间，单位秒
     */
    int timeout() default 60;

    /**
     * 方法内部发送异常是否抛出
     */
    boolean throwException() default false;
}
