package xyz.jereznx.spring.redis.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring应用上下文通用类，用于支持普通java类获取Spring Bean
 *
 * @author Jerez
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext context;
    private static final byte[] LOCK = new byte[0];

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContextUtil.context = context;
        synchronized (LOCK) {
            try {
                LOCK.notifyAll();
            } catch (IllegalMonitorStateException e) {
                throw new FatalBeanException("SpringContextUtil init failed", e);
            }
        }
    }

    private static ApplicationContext getApplicationContext() {
        if (context == null) {
            synchronized (LOCK) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return context;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
