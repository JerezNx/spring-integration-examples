package xyz.jereznx.spring.mybatis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.jereznx.spring.mybatis.plugin.ExamplePlugin;

/**
 * @author liqilin
 * @since 2020/10/14 14:59
 */
@Configuration
public class PluginConfig {

    @Bean
    public ExamplePlugin examplePlugin() {
        return new ExamplePlugin();
    }

}
