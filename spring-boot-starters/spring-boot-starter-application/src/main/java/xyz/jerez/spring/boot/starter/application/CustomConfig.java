package xyz.jerez.spring.boot.starter.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.jerez.spring.boot.starter.demo.DemoBean;

/**
 * @author liqilin
 * @since 2021/4/10 15:23
 */
//@Configuration
public class CustomConfig {

    @Bean
    public DemoBean demoBean() {
        return new DemoBean("custom");
    }

}
