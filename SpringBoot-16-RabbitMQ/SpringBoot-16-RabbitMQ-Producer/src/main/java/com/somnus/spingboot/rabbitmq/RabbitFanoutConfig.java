package com.somnus.spingboot.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: RabbitFanoutConfig
 * @Description: Fanout 就是我们熟悉的广播模式或者订阅模式，
 *               给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息
 * @author pc
 * @date 2018年8月31日
 */
@Configuration
public class RabbitFanoutConfig {

    @Bean
    public Queue smileQueue() {
        return new Queue("fanout.smile.net");
    }

    @Bean
    public Queue somnusQueue() {
        return new Queue("fanout.somnus.com");
    }

    /**
     * 任何发送到Fanout Exchange的消息都会被转发到与该Exchange绑定(Binding)的所有队列上。
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    public Binding bindingExchangeSmileQueue(Queue smileQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(smileQueue).to(fanoutExchange);
    }

    @Bean
    public Binding bindingExchangeSomnusQueue(Queue somnusQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(somnusQueue).to(fanoutExchange);
    }

}