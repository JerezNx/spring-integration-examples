package xyz.jerez.spring.json.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author liqilin
 * @since 2021/3/5 15:24
 */
@Profile("fastjson")
@Configuration
public class FastJsonConfig implements WebMvcConfigurer {

    /*--------------------------------- spring mvc json转换 --------------------------------------*/

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(fastJsonHttpMessageConverter());
    }

    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
//        todo 额外配置
        return new FastJsonHttpMessageConverter();
    }

    /*--------------------------------- restTemplate json转换 --------------------------------------*/

    @Bean
    public RestTemplate restTemplate() {
        final RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
        final List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
//        将默认的jackson移除
        messageConverters.removeIf(messageConverter -> messageConverter instanceof MappingJackson2HttpMessageConverter);
//        换成fastJson
        messageConverters.add(fastJsonHttpMessageConverter());
        return restTemplate;
    }

    @Bean
    public SimpleClientHttpRequestFactory httpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        return factory;
    }

}
