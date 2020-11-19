package xyz.jerez.spring.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 指定top partition 进行消费
 *
 * @author liqilin
 * @since 2020/10/28 17:11
 */
@Slf4j
@Component
public class TopicPartitionListener {

    /**
     * 指定消费哪个partition,offset从哪开始
     * @param data
     */
    @KafkaListener(id = "batchWithPartition", clientIdPrefix = "bwp", containerFactory = "batchContainerFactory",
            topicPartitions = {
                    @TopicPartition(topic = "topic.quick.batch.partition", partitions = {"1", "3"}),
                    @TopicPartition(topic = "topic.quick.batch.partition", partitions = {"0", "4"},
                            partitionOffsets = @PartitionOffset(partition = "2", initialOffset = "10"))
            }
    )
    public void batchListenerWithPartition(List<String> data) {
        log.info("topic.quick.batch.partition  receive : ");
        for (String s : data) {
            log.info(s);
        }
    }

}
