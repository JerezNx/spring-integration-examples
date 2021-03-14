package xyz.jereznx.spring.mybatis.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.jereznx.spring.mybatis.domain.BaseEnum;
import xyz.jereznx.spring.mybatis.handler.MyBaseTypeHandler;
import xyz.jereznx.spring.mybatis.util.ClassUtil;

import java.util.List;

/**
 * 注册handler
 *
 * @author liqilin
 * @since 2021/2/5 13:44
 */
@Slf4j
@Configuration
public class HandlerConfig {

    /**
     * mybatis 实体枚举转换
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            try {
//                注册BaseEnum的所有实现枚举
                final List<Class<?>> allAssignedClass = ClassUtil.getAllAssignedClass(BaseEnum.class);
                allAssignedClass.forEach((clazz) -> typeHandlerRegistry.register(clazz, MyBaseTypeHandler.class));
            } catch (Exception e) {
                log.error("注册枚举Handler发生错误", e);
            }
        };
    }

}
