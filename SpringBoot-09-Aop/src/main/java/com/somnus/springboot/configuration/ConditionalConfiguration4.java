package com.somnus.springboot.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
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
/* 指定该Configuration类的加载顺序，默认值0 */
@AutoConfigureOrder(4)
public class ConditionalConfiguration4 {

    @Bean
    public ApplicationTemp applicationTemp4(){
        System.out.println("④");
        return new ApplicationTemp();
    }
}
