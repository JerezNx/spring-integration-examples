package xyz.jerez.spring.ioc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import xyz.jerez.spring.ioc.bean.LifeCycleDemo;
import xyz.jerez.spring.ioc.bean.LifeCycleDemo2;

import java.util.concurrent.TimeUnit;

/**
 * @author liqilin
 * @since 2021/2/26 17:15
 */
@Configuration
public class BeanRegister {

    @Bean(initMethod = "initMethodTest",destroyMethod = "destroyMethodTest")
    public LifeCycleDemo lifeCycleDemo() {
        return new LifeCycleDemo();
    }

    @Bean(initMethod = "initMethodTest")
    public LifeCycleDemo2 lifeCycleDemo2() {
        return new LifeCycleDemo2();
    }
}
