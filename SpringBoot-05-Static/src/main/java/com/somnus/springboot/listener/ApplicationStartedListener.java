package com.somnus.springboot.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @title: SpringApplicationListener
 * @description: TODO
 * @date 2020/4/20 16:12
 */
@Slf4j
@Component
public class ApplicationStartedListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        System.out.println("***************ApplicationStartedEvent" + event.getApplicationContext().getDisplayName());
        if (event.getApplicationContext().getParent() == null){
            System.out.println("====================ApplicationStartedEvent====================");
        }
    }
}
