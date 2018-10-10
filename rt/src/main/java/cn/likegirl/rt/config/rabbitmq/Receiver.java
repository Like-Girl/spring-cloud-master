package cn.likegirl.rt.config.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

@Component
public class Receiver {

    @RabbitListener(queues = "#{autoDeleteQueue0.name}")
    public void receiver0(Message message, Channel channe) throws IOException {
        try {
            Random random = new Random();
            int code = random.nextInt(10);
            if(code % 2 == 0){
                throw new NullPointerException();
            }
            System.out.println("receiver0++++++++++:" + new String(message.getBody()));
            channe.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (NullPointerException e) {
            channe.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
        }
    }

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receiver1(Message message, Channel channe) throws IOException {
        System.out.println("receiver1++++++++++:" + new String(message.getBody()));
        channe.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
