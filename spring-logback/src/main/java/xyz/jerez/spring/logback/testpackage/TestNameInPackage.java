package xyz.jerez.spring.logback.testpackage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 根据name输出日志
 * @author liqilin
 * @since 2020/10/26 13:08
 */
@Component
public class TestNameInPackage {

    /**
     * 如果是用name获取的logger对象，则输出到name中，不管在哪个包下
     */
    private Logger log = LoggerFactory.getLogger("testName");

    public void print(String msg) {
        log.info(msg);
    }

}
