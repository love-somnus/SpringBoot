package com.somnus.spingboot.rabbitmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.somnus.spingboot.rabbitmq.domain.Order;

@Slf4j
@Service
public class FanoutProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 此消息在，广播模式或者订阅模式队列下，有 FanoutComsumer【smile,somnus】 可以收到
     * @param order 消息内容
     */
    public void sendSmile(Order order) {
        String exchange = "fanoutExchange";
        String routeKey = "topic.smile.net";
        log.info("exchange:{}, routeKey :{}-->message : {}", exchange, routeKey, order);
        this.rabbitTemplate.convertAndSend(exchange, routeKey, order);
    }

    /**
     * 此消息在，广播模式或者订阅模式队列下，有 FanoutComsumer【smile,somnus】 可以收到
     * @param order 消息内容
     */
    public void sendSomnus(Order order) {
        String exchange = "fanoutExchange";
        String routeKey = "topic.somnus.net";
        log.info("exchange:{}, routeKey :{}-->message : {}", exchange, routeKey, order);
        this.rabbitTemplate.convertAndSend(exchange, routeKey, order);
    }

}
