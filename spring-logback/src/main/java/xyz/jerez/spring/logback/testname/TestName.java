package xyz.jerez.spring.logback.testname;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 根据name输出日志
 * @author liqilin
 * @since 2020/10/26 13:08
 */
@Component
public class TestName {

    private Logger log = LoggerFactory.getLogger("testName");

    public void print(String msg) {
        log.info(msg);
    }

}
