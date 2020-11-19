package xyz.jerez.spring.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author liqilin
 * @since 2020/10/28 13:58
 */
@Slf4j
@Component
public class AnnotationListener {

    @KafkaListener(topics = "test")
    public void onMessage(String message) {
        log.info("message:{}", message);
    }

    /**
     * 默认id就是groupId
     * @param record
     */
    @KafkaListener(id = "test1",topics = "test")
    public void onMessage(ConsumerRecord<Integer,String> record) {
        log.info("record:{}", record);
    }

    /**
     * 通过注解直接获取元信息
     * @param data
     * @param key
     * @param partition
     * @param topic
     * @param ts
     */
    @KafkaListener(id = "anno", topics = "test")
    public void onMessage(@Payload String data,
                             @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) Object key,
                             @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                             @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                             @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
        log.info("topic.quick.anno receive : \n"+
                "data : "+data+"\n"+
                "key : "+key+"\n"+
                "partitionId : "+partition+"\n"+
                "topic : "+topic+"\n"+
                "timestamp : "+ts+"\n"
        );
    }

}
