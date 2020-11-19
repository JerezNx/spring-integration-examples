package xyz.jerez.spring.logback.testpackage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 根据package输出日志
 * @author liqilin
 * @since 2020/10/26 13:05
 */
@Slf4j
@Component
public class TestPackage {

    public void print(String msg) {
        log.info(msg);
    }
}
