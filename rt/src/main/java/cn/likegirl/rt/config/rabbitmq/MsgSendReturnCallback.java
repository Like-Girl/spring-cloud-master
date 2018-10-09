package cn.likegirl.rt.config.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;

/**
 * 消息回调
 * 
 * @author xuliqi
 *
 */
public class MsgSendReturnCallback implements ReturnCallback {

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        String correlationId = message.getMessageProperties().getCorrelationIdString();
        System.out.println("消息： {} 发送失败,应答码： {} 原因：{} 交换机：{} 路由键：{}"+correlationId+replyCode+ 
                replyText+exchange+routingKey);

    }
}
