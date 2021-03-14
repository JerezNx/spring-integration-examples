package xyz.jereznx.spring.redis.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.Objects;

/**
 * @author Jerez
 */
@Slf4j
public class RedisMqClient {

    private static RedisTemplate<String, String> redisTemplate;

    private static boolean inited;

    public RedisMqClient() {
        if (!inited) {
            throw new IllegalArgumentException("call method init once before use");
        }
    }

    /**
     * @param topic
     */
    public RedisMessageListenerContainer subscribe(String topic, MessageHandler handler) {
        MessageAdapter messageAdapter = new MessageAdapter(handler);
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        redisMessageListenerContainer.addMessageListener(messageAdapter, new PatternTopic(topic));
        return redisMessageListenerContainer;
    }

    public void publish(String topic, String message) {
        redisTemplate.convertAndSend(topic, message);
    }

    /**
     * 全局需且必需在使用该类前调用1次
     */
    public static void init(RedisTemplate<String, String> redisTemplate) {
        RedisMqClient.redisTemplate = redisTemplate;
        inited = true;
    }

    private static class MessageAdapter implements MessageListener {

        private MessageHandler handler;

        MessageAdapter(MessageHandler handler) {
            this.handler = handler;
        }

        @Override
        public void onMessage(Message message, byte[] bytes) {
            byte[] body = message.getBody();
            byte[] channel = message.getChannel();
            String msg = (String) redisTemplate.getValueSerializer().deserialize(body);
            String topic = redisTemplate.getStringSerializer().deserialize(channel);
            handler.handle(msg, topic);
        }
    }

}
