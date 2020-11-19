package xyz.jereznx.spring.mybatis.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * @author liqilin
 * @since 2020/10/14 14:57
 */
@Slf4j
// 同一个拦截器可以拦截多个方法
@Intercepts({@Signature(
//        定义要拦截哪个类
        type = Executor.class,
//        定义要拦截类中的哪个方法，method和args 组合，确定唯一方法
//        定义方法名
        method = "query",
//        定义方法的参数
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class ExamplePlugin implements Interceptor {
    private Properties properties = new Properties();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("args:{}", invocation.getArgs());
//        args 即 拦截的方法的参数
        // implement pre-processing if needed
        Object returnObject = invocation.proceed();
        // implement post-processing if needed
        log.info("res:{}", returnObject);
        return returnObject;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
