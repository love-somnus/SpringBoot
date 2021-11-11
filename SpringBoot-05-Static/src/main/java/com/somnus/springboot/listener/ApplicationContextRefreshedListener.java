package com.somnus.springboot.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @title: SpringApplicationListener
 * @description: TODO
 * @date 2020/4/20 16:12
 */
@Slf4j
@Component
public class ApplicationContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("***************ContextRefreshedEvent" + event.getApplicationContext().getDisplayName());
        if (event.getApplicationContext().getParent() == null){
            System.out.println("====================ContextRefreshedEvent====================");
        }
    }
}
