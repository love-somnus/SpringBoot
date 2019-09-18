package com.somnus.springboot;

import com.somnus.spingboot.RabbitmqProducerApplication;
import com.somnus.spingboot.rabbitmq.producer.DirectProducer;
import com.somnus.spingboot.rabbitmq.domain.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RabbitmqProducerApplication.class)
public class RabbitMQDirectTest {

    @Autowired
    private DirectProducer producer;

    @Test
    public void send() {
        producer.send(new Order(1, "100"));
    }

}
