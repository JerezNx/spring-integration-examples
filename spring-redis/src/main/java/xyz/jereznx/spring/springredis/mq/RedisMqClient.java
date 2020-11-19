package xyz.jereznx.spring.springredis.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Slf4j
public class RedisMqClient {

    private RedisTemplate<String, String> redisTemplate;

    public RedisMqClient( RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * @param topic
     */
    public RedisMessageListenerContainer subscribe(String topic, MessageHandler handler) {
        MessageAdapter messageAdapter = new MessageAdapter(handler);
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisTemplate.getConnectionFactory());
        redisMessageListenerContainer.addMessageListener(messageAdapter, new PatternTopic(topic));
        return redisMessageListenerContainer;
    }

    public void publish(String topic, String message) {
        redisTemplate.convertAndSend(topic, message);
    }

    private class MessageAdapter implements MessageListener {
        private MessageHandler handler;
        public MessageAdapter(MessageHandler handler) {
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
