package xyz.jerez.spring.quartz.config;

import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * 自动装配参见 {@link org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration}
 *
 * @author liqilin
 * @since 2020/11/10 14:44
 */
//@Configuration
public class QuartzConfig {

    @QuartzDataSource
    @ConfigurationProperties(prefix = "quartz.datasource")
    @Bean
    DataSource quartzDataSource() {
        return DataSourceBuilder.create().build();
    }

}
