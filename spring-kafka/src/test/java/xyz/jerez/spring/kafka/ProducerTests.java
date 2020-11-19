package xyz.jerez.spring.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author liqilin
 * @since 2020/10/28 13:59
 */
@SpringBootTest
public class ProducerTests {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    void sendTest() {
        kafkaTemplate.send("test", "hello world");
    }

    @Test
    void testBatch() {
        for (int i = 0; i < 12; i++) {
            kafkaTemplate.send("test.batch", "test batch listener,dataNum-" + i);
        }
    }

    @Test
    void testTopicPartitionBatch() {
        for (int i = 0; i < 120; i++) {
            kafkaTemplate.send("topic.quick.batch.partition", "test topic partition listener,dataNum-" + i);
        }
    }
}
