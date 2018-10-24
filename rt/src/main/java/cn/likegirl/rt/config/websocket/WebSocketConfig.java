package cn.likegirl.rt.config.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Autowired
    private AuthWebSocketHandlerDecoratorFactory myWebSocketHandlerDecoratorFactory;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/my-websocket").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /**
         * 配置消息代理
         * 使用RabbitMQ做为消息代理，替换默认的Simple Broker
         */
//        registry.enableSimpleBroker("/topic");
        // 指服务端接收地址的前缀，意思就是说客户端给服务端发消息的地址的前缀
        registry.setApplicationDestinationPrefixes("/app")
//                // "STOMP broker relay"处理所有消息将消息发送到外部的消息代理
                // exchange 交换机 -》 /exchange/<exchange>/<routingKey> 例如： /exchange/amq.topic/ke
                .enableStompBrokerRelay("/exchange","/topic","/queue","/amq/queue")
                .setVirtualHost("/")
                .setRelayHost("114.67.226.113")
                .setClientLogin("admin")
                .setClientPasscode("123456")
                .setSystemLogin("admin")
                .setSystemPasscode("123456")
                .setSystemHeartbeatSendInterval(5000)
                .setSystemHeartbeatReceiveInterval(4000);
    }

    /**
     * 这时实际spring weboscket集群的新增的配置，用于获取建立websocket时获取对应的sessionid值
     * @param registration
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(myWebSocketHandlerDecoratorFactory);
        super.configureWebSocketTransport(registration);
    }


}