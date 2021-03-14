package xyz.jerez.spring.ioc.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author liqilin
 * @since 2021/2/26 17:09
 */
public class LifeCycleDemo implements InitializingBean, DisposableBean {

    @Value("${di.value:hello}")
    private String value;

    @Autowired
    private Common common;

    @PostConstruct
    public void postConstructTest() {
        print("1 @PostConstruct");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        print("2 InitializingBean.afterPropertiesSet");
    }

    public void initMethodTest() {
        print("3 initMethod");
    }

    @PreDestroy
    public void preDestroyTest() {
        print("4 @PreDestroy");
    }

    @Override
    public void destroy() throws Exception {
        print("5 DisposableBean.destroy");
    }

    public void destroyMethodTest() {
        print("6 destroyMethod");
    }

    public void print(String msg) {
        System.out.println(this.getClass().getName() + "|" + msg + "|" + value + "|" + common);
    }

}
