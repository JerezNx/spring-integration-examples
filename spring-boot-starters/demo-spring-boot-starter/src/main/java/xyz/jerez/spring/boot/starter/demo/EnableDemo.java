package xyz.jerez.spring.boot.starter.demo;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author liqilin
 * @since 2021/4/6 17:44
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DemoAutoConfiguration.class)
public @interface EnableDemo {
}
