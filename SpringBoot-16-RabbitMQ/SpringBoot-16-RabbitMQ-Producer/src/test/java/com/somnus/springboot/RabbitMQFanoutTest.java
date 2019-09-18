package com.somnus.springboot;

import com.somnus.spingboot.RabbitmqProducerApplication;
import com.somnus.spingboot.rabbitmq.producer.FanoutProducer;
import com.somnus.spingboot.rabbitmq.domain.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RabbitmqProducerApplication.class)
public class RabbitMQFanoutTest {

    @Autowired
    private FanoutProducer producer;

    @Test
    public void sendSmile() {
        producer.sendSmile(new Order(1, "100"));
    }

    @Test
    public void sendSomnus() {
        producer.sendSomnus(new Order(1, "100"));
    }


}
