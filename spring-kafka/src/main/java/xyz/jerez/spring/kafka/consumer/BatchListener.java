package xyz.jerez.spring.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 批量消费
 *
 * @author liqilin
 * @since 2020/10/28 17:02
 */
@Slf4j
@Component
public class BatchListener {

    /**
     * 批量拉取数据
     * 设置containerFactory
     * 参数用List接收
    * @param data
     */
    @KafkaListener(id = "batch", clientIdPrefix = "batch", topics = {"test.batch"}, containerFactory = "batchContainerFactory")
    public void batchListener(List<String> data) {
        log.info("test.batch  receive : ");
        for (String s : data) {
            log.info(s);
        }
    }

}
