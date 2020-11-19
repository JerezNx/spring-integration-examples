package xyz.jerez.spring.quartz.config;

import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
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
