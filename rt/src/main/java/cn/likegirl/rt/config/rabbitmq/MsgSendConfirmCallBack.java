package cn.likegirl.rt.config.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * 确认消息是否成功发送给交换机
 * @author xuliqi
 *
 */
public class MsgSendConfirmCallBack implements ConfirmCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println(" 回调消息id:" + correlationData);
        if (ack) {
        	 System.out.println("消息发送确认成功");
        } else {
        	 System.out.println("消息发送确认失败:" + cause+"\n重新发送"); 

        }
    }
}
