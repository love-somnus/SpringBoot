package com.somnus.spingboot.rabbitmq.producer;

import com.somnus.spingboot.rabbitmq.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.somnus.spingboot.rabbitmq.domain.Order;

@Slf4j
@Service
public class DeadLetterProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * @param order 消息内容
     */
    public void sendOrder(Order order){
        // 添加延时队列
        this.rabbitTemplate.convertAndSend(RabbitConfig.ORDER_DELAY_EXCHANGE, RabbitConfig.ORDER_DELAY_ROUTING_KEY, order, message -> {
            // TODO 第一句是可要可不要,根据自己需要自行处理
            message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, Order.class.getName());
            // TODO 如果配置了 params.put("x-message-ttl", 60 * 1000); 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间
            message.getMessageProperties().setExpiration(60* 1000 + "");
            return message;
        });
    }

}
