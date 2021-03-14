package xyz.jerez.spring.ioc.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author liqilin
 * @since 2021/2/26 17:09
 */
public class LifeCycleDemo2 implements InitializingBean, AutoCloseable {

    @PostConstruct
    public void postConstructTest() {
        System.out.println("1 @PostConstruct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("2 InitializingBean.afterPropertiesSet");
    }

    public void initMethodTest() {
        System.out.println("3 initMethod");
    }

    @PreDestroy
    public void preDestroyTest() {
        System.out.println("4 @PreDestroy");
    }

    /**
     * 当一个bean 既没有 {@link DisposableBean#destroy()},
     * 也没有 {@link Bean#destroyMethod()} 时
     * 如果实现了 {@link AutoCloseable},则会在销毁时调用{@link AutoCloseable#close()}
     */
    @Override
    public void close() throws Exception {
        System.out.println("5 AutoCloseable.close");
    }

}
