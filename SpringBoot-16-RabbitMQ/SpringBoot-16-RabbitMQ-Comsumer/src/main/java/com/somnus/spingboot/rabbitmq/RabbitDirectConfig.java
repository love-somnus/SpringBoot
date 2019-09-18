package com.somnus.spingboot.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: RabbitDirectConfig
 * @Description: Direct Exchange是RabbitMQ默认的交换机模式，
 *               也是最简单的模式，根据key全文匹配去寻找队列
 * @author pc
 * @date 2018年8月31日
 */
@Configuration
public class RabbitDirectConfig {

    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }
    
}
