package xyz.jereznx.spring.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import xyz.jereznx.spring.redis.distributed.DistributedCountDownLatch;
import xyz.jereznx.spring.redis.mq.RedisMqClient;

import java.net.UnknownHostException;

/**
 * redis 配置
 *
 * @author LQL
 * @since Create in 2020/8/16 22:50
 */
@Configuration
public class RedisConfiguration implements InitializingBean {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisTemplate<String, Integer> intRedisTemplate;

    @Bean
    public RedisTemplate<String, Integer> intRedisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<String, Integer> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
//        starter中已经有配好了的，不必自己再进行配置
        template.setDefaultSerializer(RedisSerializer.json());
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());
        return template;
    }

    @Primary
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
//        template.setDefaultSerializer(jackson2JsonRedisSerializer());
//        默认的json 如果 值的字符串，会拼接双引号
        template.setDefaultSerializer(RedisSerializer.string());
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());
        return template;
    }

    /**
     * json序列化
     *
     * @return
     */
//    @Bean
    @SuppressWarnings("unchecked")
    public RedisSerializer<Object> jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//        设置转换的属性
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//         此项必须配置，否则会报java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to XXX
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        serializer.setObjectMapper(mapper);
        return serializer;
    }

    @Bean
    public RedisMqClient redisMqClient() throws UnknownHostException {
        return new RedisMqClient();
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisMqClient redisMqClient) throws UnknownHostException {
        return redisMqClient.subscribe("test", (message, topic) -> {
            System.out.println("收到消息：" + message + ":" + topic);
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RedisMqClient.init(redisTemplate);
        DistributedCountDownLatch.init(intRedisTemplate);
    }
}
