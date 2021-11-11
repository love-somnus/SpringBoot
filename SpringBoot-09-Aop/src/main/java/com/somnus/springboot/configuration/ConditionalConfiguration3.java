package com.somnus.springboot.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.system.ApplicationTemp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kevin.liu
 * @title: ConditionalConfiguration2
 * @projectName github
 * @description: TODO
 * @date 2021/10/29 13:50
 */
@Configuration
/* 在指定的Configuration类之后加载 */
@AutoConfigureAfter(ConditionalConfiguration2.class)
public class ConditionalConfiguration3 {

    @Bean
    public ApplicationTemp applicationTemp3(){
        System.out.println("③");
        return new ApplicationTemp();
    }
}
