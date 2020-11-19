package xyz.jerez.spring.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;

/**
 * java 代码方式消费消息
 *
 * @author liqilin
 * @since 2020/10/28 16:38
 */
@Slf4j
@Configuration
public class JavaListener {

    @Bean
    public KafkaMessageListenerContainer demoListenerContainer(ConsumerFactory<?, ?> consumerFactory) {
        ContainerProperties properties = new ContainerProperties("test");
        properties.setGroupId("bean");
        properties.setMessageListener(new MessageListener<Integer, String>() {
            @Override
            public void onMessage(ConsumerRecord<Integer, String> record) {
                log.info("java topic receive : " + record.value());
            }
        });

        return new KafkaMessageListenerContainer(consumerFactory, properties);
    }

}
