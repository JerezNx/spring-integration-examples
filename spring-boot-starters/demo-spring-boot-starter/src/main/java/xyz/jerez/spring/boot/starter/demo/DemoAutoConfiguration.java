package xyz.jerez.spring.boot.starter.demo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liqilin
 * @since 2021/4/6 16:51
 */
@Configuration
public class DemoAutoConfiguration {

    @Bean
//    @ConditionalOnMissingBean
//    @ConditionalOnProperty(prefix = "demo", name = "enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnExpression("'${demo.enabled:null}' != 'null'")
    public DemoBean demoBean() {
        return new DemoBean(demoProperties().getName());
    }

    @Bean
    @ConfigurationProperties(prefix = DemoProperties.PREFIX)
    public DemoProperties demoProperties() {
        return new DemoProperties();
    }

}
