package com.somnus.spingboot.rabbitmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.somnus.spingboot.rabbitmq.domain.Order;

@Slf4j
@Service
public class TopicProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 此消息在，配置转发消息模式队列下，有 TopicComsumer【message,messages,ymq】 可以收到
     * @param order 消息内容
     */
    public void sendMessage(Order order) {
        String exchange = "topicExchange";
        String routeKey = "topic.message";
        log.info("exchange:{}, routeKey :{}-->message : {}", exchange, routeKey, order);
        this.rabbitTemplate.convertAndSend(exchange, routeKey, order);
    }

    /**
     * 此消息在，配置转发消息模式队列下，有 TopicComsumer【messages,ymq】 可以收到
     * @param order 消息内容
     */
    public void sendMessages(Order order) {
        String exchange = "topicExchange";
        String routeKey = "topic.message.s";
        log.info("exchange:{}, routeKey :{}-->message : {}", exchange, routeKey, order);
        this.rabbitTemplate.convertAndSend(exchange, routeKey, order);
    }

    /**
     * 此消息在，配置转发消息模式队列下，有 TopicReceiver【ymq】 可以收到
     * @param order 消息内容
     */
    public void sendYmq(Order order) {
        String exchange = "topicExchange";
        String routeKey = "topic.ymq";
        log.info("exchange:{}, routeKey :{}-->message : {}", exchange, routeKey, order);
        this.rabbitTemplate.convertAndSend(exchange, routeKey, order);
    }

}
