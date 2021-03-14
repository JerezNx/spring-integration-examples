package xyz.jerez.nx.spring.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liqilin
 * @since 2020/11/28 13:29
 */
@Configuration
public class MyWebMvcConfig {

    @Bean
    public WebMvcConfigurer enumConverterConfigurer() {
        System.out.println("111111111111111111");
        return new WebMvcConfigurer() {

            @Override
            public void addFormatters(FormatterRegistry registry) {
                System.out.println("2222222222");
//                registry.addConverterFactory(new StringToEnumConverterFactory());
            }
        };
    }

}
