package cn.likegirl.rt.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    /**
     * RabbitTemplate 发送消息，必须是prototype类型，
     * 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置
     *
     * @return
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate();

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
     * 声明队列
     */
    @Bean
    public Queue queueTransaction() {
        // true表示持久化该队列
        return new Queue(RabbitConstant.QUEUE_TRANSACTION, true);
    }

    @Bean
    public Queue queueContract() {
        // true表示持久化该队列
        return new Queue(RabbitConstant.QUEUE_CONTRACT, true);
    }

    @Bean
    public Queue queueQualification() {
        // true表示持久化该队列
        return new Queue(RabbitConstant.QUEUE_QUALIFICATION, true);
    }

    /**
     * 声明交互器
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(RabbitConstant.EXCHANGE);
    }

    /**
     * 绑定
     *
     */
    @Bean
    public Binding bindingTransaction() {
        return BindingBuilder.bind(queueTransaction()).to(directExchange()).with(RabbitConstant.RK_TRANSACTION);
    }

    /**
     * 绑定
     *
     */
    @Bean
    public Binding bindingContract() {
        return BindingBuilder.bind(queueContract()).to(directExchange()).with(RabbitConstant.RK_CONTRACT);
    }

    /**
     * 绑定
     *
     */
    @Bean
    public Binding bindingQualification() {
        return BindingBuilder.bind(queueQualification()).to(directExchange()).with(RabbitConstant.RK_QUALIFICATION);
    }


}
