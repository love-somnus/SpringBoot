package com.somnus.spingboot.rabbitmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.somnus.spingboot.rabbitmq.domain.Order;

@Slf4j
@Service
public class DirectProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 默认的交换机模式队列下，有 DirectComsumer【hello】 可以收到
     * @param order 消息内容
     */
    public void send(Order order){
        String routeKey = "hello";
        log.info("routeKey :{}-->message : {}", routeKey, order);
        this.rabbitTemplate.convertAndSend(routeKey, order);
    }

}
