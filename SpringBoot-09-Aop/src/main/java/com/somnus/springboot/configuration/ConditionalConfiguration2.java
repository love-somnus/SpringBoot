package com.somnus.springboot.configuration;

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
public class ConditionalConfiguration2 {

    @Bean
    public ApplicationTemp applicationTemp(){
        System.out.println("②");
        return new ApplicationTemp();
    }
}
