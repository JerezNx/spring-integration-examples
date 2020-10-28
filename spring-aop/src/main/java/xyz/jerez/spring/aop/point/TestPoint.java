package xyz.jerez.spring.aop.point;

import org.springframework.stereotype.Component;
import xyz.jerez.spring.aop.annotation.Log;

/**
 * @author liqilin
 * @since 2020/10/28 15:35
 */
@Component
public class TestPoint {

    @Log("233")
    public String testLog(String param) {
        return "log:" + param;
    }

    @Log("233")
    @Log("666")
    public String testLogs(String param) {
        return "logs:" + param;
    }
}
