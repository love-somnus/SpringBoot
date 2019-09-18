package com.somnus.spingboot.rabbitmq.comsumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.somnus.spingboot.rabbitmq.domain.Order;

@Slf4j
@Component
public class FanoutComsumer {

    @RabbitListener(queues = "fanout.smile.net")
    public void smile(Order order, Channel channel, Message message) throws Exception{
        log.info("fanout.smile.net：{}", order);
        try {
            //告诉MQ收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            log.info("receiver success");
        } catch (Exception e) {
            //让RabbitMQ把这条消息重新投递给其他的仓储服务实例，因为自己没处理成功
            //设置为false的话，就会导致RabbitMQ知道你处理失败，但是还是删除这条消息，这是不对的
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
            log.info("receiver fail");
        }
    }

    @RabbitListener(queues = "fanout.somnus.com")
    public void somnus(Order order, Channel channel, Message message) throws Exception{
        log.info("fanout.somnus.com：{}", order);
        try {
            //告诉MQ收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            log.info("receiver success");
        } catch (Exception e) {
            //让RabbitMQ把这条消息重新投递给其他的仓储服务实例，因为自己没处理成功
            //设置为false的话，就会导致RabbitMQ知道你处理失败，但是还是删除这条消息，这是不对的
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
            log.info("receiver fail");
        }
    }
}
