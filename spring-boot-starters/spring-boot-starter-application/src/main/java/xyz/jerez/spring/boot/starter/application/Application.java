package xyz.jerez.spring.boot.starter.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import xyz.jerez.spring.boot.starter.demo.DemoAutoConfiguration;
import xyz.jerez.spring.boot.starter.demo.DemoBean;
import xyz.jerez.spring.boot.starter.demo.EnableDemo;

/**
 * @author liqilin
 * @since 2021/4/6 16:58
 */
//@ImportAutoConfiguration(DemoAutoConfiguration.class)
//@Import(DemoAutoConfiguration.class)
@EnableDemo
//@SpringBootApplication(scanBasePackages = "xyz.jerez.spring")
@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        final ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        final DemoBean bean = context.getBean(DemoBean.class);
        bean.doSth();
    }

}
