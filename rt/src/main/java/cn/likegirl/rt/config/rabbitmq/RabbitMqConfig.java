package cn.likegirl.rt.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author LikeGirl
 * @version v1.0
 * @title: RabbitMqConfig
 * @description: TODO
 * @date 2018/10/9 17:53
 */
@Configuration
public class RabbitMqConfig {

    @Autowired
    ConnectionFactory connectionFactory;

    /**
     * RabbitTemplate 发送消息，必须是prototype类型，
     * 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置
     *
     * @return
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);

        /**若使用confirm-callback或return-callback，
         * 必须要配置publisherConfirms或publisherReturns为true
         * 每个rabbitTemplate只能有一个confirm-callback和return-callback
         * */
        template.setConfirmCallback(msgSendConfirmCallBack());
        template.setReturnCallback(msgSendReturnCallback());

        /**
         * 使用return-callback时必须设置mandatory为true，
         * 或者在配置中设置mandatory-expression的值为true，
         * 可针对每次请求的消息去确定’mandatory’的boolean值，
         * 只能在提供’return -callback’时使用，与mandatory互斥
         * */
        // 保证消息的可靠投递
        template.setMandatory(true);
        return template;
    }

    /**
     * 消息确认机制
     * Confirms给客户端一种轻量级的方式，能够跟踪哪些消息被broker处理，
     * 哪些可能因为broker宕掉或者网络失败的情况而重新发布。
     * 确认并且保证消息被送达，提供了两种方式：发布确认和事务。
     * (两者不可同时使用)在channel为事务时，
     * 不可引入确认模式；同样channel为确认模式下，不可使用事务。
     */
    @Bean
    public MsgSendConfirmCallBack msgSendConfirmCallBack() {
        return new MsgSendConfirmCallBack();
    }

    /**
     * 消息未发送到交换机调用
     */
    @Bean
    public MsgSendReturnCallback msgSendReturnCallback() {
        return new MsgSendReturnCallback();
    }


    /**
     * 时间：2018/3/5 上午10:45
     * @apiNote 定义扇出（广播）交换器
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitConstant.EXCHANGE);
    }

    /**
     * 时间：2018/3/5 上午10:48
     * @apiNote 定义自动删除匿名队列
     */
    @Bean("autoDeleteQueue0")
    public Queue autoDeleteQueue0() {
        return new AnonymousQueue();
    }

    /**
     * 时间：2018/3/5 上午10:48
     * @apiNote 定义自动删除匿名队列
     */
    @Bean("autoDeleteQueue1")
    public Queue autoDeleteQueue1() {
        return new AnonymousQueue();
    }

    /**
     * 时间：2018/3/5 上午10:48
     * @param fanoutExchange 扇出（广播）交换器
     * @param autoDeleteQueue0 自动删除队列
     * @apiNote 把队列绑定到扇出（广播）交换器
     * @return Binding
     */
    @Bean
    public Binding binding0(FanoutExchange fanoutExchange,@Qualifier("autoDeleteQueue0") Queue autoDeleteQueue0) {
        return BindingBuilder.bind(autoDeleteQueue0).to(fanoutExchange);
    }

    /**
     * 时间：2018/3/5 上午10:55
     * @param fanoutExchange 扇出（广播）交换器
     * @param autoDeleteQueue1 自动删除队列
     * @apiNote 把队列绑定到扇出（广播）交换器
     * @return Binding
     */
    @Bean
    public Binding binding1(FanoutExchange fanoutExchange,@Qualifier("autoDeleteQueue1") Queue autoDeleteQueue1) {
        return BindingBuilder.bind(autoDeleteQueue1).to(fanoutExchange);
    }

}
