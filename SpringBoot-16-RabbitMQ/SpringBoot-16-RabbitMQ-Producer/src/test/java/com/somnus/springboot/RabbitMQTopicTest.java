package com.somnus.springboot;

import com.somnus.spingboot.RabbitmqProducerApplication;
import com.somnus.spingboot.rabbitmq.producer.TopicProducer;
import com.somnus.spingboot.rabbitmq.domain.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RabbitmqProducerApplication.class)
public class RabbitMQTopicTest {

    @Autowired
    private TopicProducer producer;

    @Test
    public void sendMessage() {
        producer.sendMessage(new Order(1, "100"));
    }

    @Test
    public void sendMessages() {
        producer.sendMessages(new Order(1, "100"));
    }

    @Test
    public void sendYmq() {
        producer.sendYmq(new Order(1, "100"));
    }


}
