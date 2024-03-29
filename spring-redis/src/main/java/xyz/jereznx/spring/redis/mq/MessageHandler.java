package xyz.jereznx.spring.redis.mq;

/**
 * @author Jerez
 */
public interface MessageHandler {

    /**
     * 消息处理方法
     * @param message 消息内容
     * @param topic 消息主题
     */
    void handle(String message, String topic);

}
