package com.somnus.springboot.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2018/12/21 17:00
 */
@Slf4j
@Component
public class Producer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 发送消息到kafka
     */
    public void sendChannelMessage(String message){
        String channel = "yuandan";
        log.info("★★★★★★★★★★ channel :{}-->message : {}", channel, message);
        kafkaTemplate.send(channel,message);
    }
}