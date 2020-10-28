package xyz.jerez.spring.aop.annotation;

import java.lang.annotation.*;

/**
 * @author liqilin
 * @since 2020/10/28 15:31
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(Logs.class)
public @interface Log {

    String value() default "";

}
