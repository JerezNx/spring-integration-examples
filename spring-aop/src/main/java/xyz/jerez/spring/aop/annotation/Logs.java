package xyz.jerez.spring.aop.annotation;

import java.lang.annotation.*;

/**
 * @author liqilin
 * @since 2020/10/28 15:41
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Logs {
    Log[] value();
}
