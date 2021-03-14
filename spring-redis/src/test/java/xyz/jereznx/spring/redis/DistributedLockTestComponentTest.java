package xyz.jereznx.spring.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.jereznx.spring.redis.distributed.DistributedLockTestComponent;

@SpringBootTest
public class DistributedLockTestComponentTest {

    @Autowired
    private DistributedLockTestComponent component;

    /**
     * 启动多实例测试
     */
    @Test
    public void doSth() throws InterruptedException {
        while (true) {
            component.doSth();
        }
    }
}
