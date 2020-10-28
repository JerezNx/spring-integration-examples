package xyz.jerez.spring.aop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.jerez.spring.aop.point.TestPoint;

@SpringBootTest
class SpringAopApplicationTests {

    @Autowired
    private TestPoint point;

    @Test
    void contextLoads() {
    }

    @Test
    void testLog() {
        point.testLog("test");
    }

    @Test
    void testLogs() {
        point.testLogs("test");
    }

}
