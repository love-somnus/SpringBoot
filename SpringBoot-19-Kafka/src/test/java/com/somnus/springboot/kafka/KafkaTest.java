package com.somnus.springboot.kafka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class KafkaTest {

    @Autowired
    private Producer producer;

    @Test
    public void sendChannelMessage() {
        producer.sendChannelMessage("happy yuandan");
        try {
            //防止Spring容器过早的关闭
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



}
