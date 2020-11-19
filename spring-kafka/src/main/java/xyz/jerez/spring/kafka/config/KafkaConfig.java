package xyz.jerez.spring.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

/**
 * @author liqilin
 * @since 2020/10/28 16:52
 */
@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic batchWithPartitionTopic() {
        return new NewTopic("topic.quick.batch.partition", 8, (short) 1);
    }

    @Bean
    public NewTopic batchTopic() {
        return new NewTopic("test.batch", 8, (short) 1);
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory batchContainerFactory(ConsumerFactory kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaConsumerFactory);
        //设置并发量，小于或等于Topic的分区数
        factory.setConcurrency(5);
        //设置为批量监听
        factory.setBatchListener(true);
        return factory;
    }


}
