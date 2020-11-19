package xyz.jereznx.spring.ws.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.jereznx.spring.ws.config.WsConfig;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;

/**
 * websocket 是 每建立一个连接，就创建一个bean，而非spring 默认的单例模式
 * 像下面的 date 变量，只有在建立连接时才会创建， 且每建立一个连接，就会new 一个新的
 * <p>
 * 在这个类中注入其他bean，只能通过set方法 注入静态成员变量，或者通过spring context 进行注入
 * 因为属性注入是在单例bean实例化后初始化时进行的，
 * 但websocket的bean 非单例，而是建立连接时创建的，所以只能通过set方法延迟注入
 *
 * @author liqilin
 * @since 2020/10/9 14:16
 */
@Slf4j
@Component
@ServerEndpoint(value = "/test/{param}")
public class TestService {

    /**
     * 建立连接的时间
     */
    private Date date = new Date();

    /**
     * 注入其他spring的bean
     * 不可直接属性注入
     */
    private static WsConfig OTHER_BEAN;

    /**
     * 延迟注入
     * @param wsConfig 其他bean
     */
    @Autowired
    public void setWsConfig(WsConfig wsConfig) {
        OTHER_BEAN = wsConfig;
    }

    /**
     * 建立连接
     *
     * @param param 参数，对应于类上的 {param}
     * @param session 会话
     * @throws Exception
     */
    @OnOpen
    public void onOpen(@PathParam("param") String param, Session session) throws Exception {
        log.info("{}", OTHER_BEAN.hashCode());
        log.info("open: date:{},param:{}", date, param);
        session.getBasicRemote().sendText(date.toString());
    }

    /**
     * 接收客户端发送的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("message:{}", message);
        session.getBasicRemote().sendText("receive message:" + message);
    }

    /**
     * 其他生命周期中发生异常，都会触发改方法
     *
     * @param session 会话
     * @param e       异常
     */
    @OnError
    public void onError(Session session, Throwable e) {
        log.error("exception:", e);
    }

    /**
     * 会话关闭事件
     */
    @OnClose
    public void onClose() {
        log.info("closed");
    }

}
