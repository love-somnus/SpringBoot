package com.somnus.springboot.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2018/12/21 17:00
 */
@Slf4j
@Component
class Consumer {

    @KafkaListener(topics = {"yuandan"})
    public void receiveMessage(String message){
        log.info("message：{}", message);
        //TODO
        //消费者拿到想要的字符串,至于怎么处理就是你自己的事情了
    }

}