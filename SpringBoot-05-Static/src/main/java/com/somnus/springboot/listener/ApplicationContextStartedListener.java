package com.somnus.springboot.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

/**
 * @title: SpringApplicationListener
 * @description: TODO
 * @date 2020/4/20 16:12
 */
@Slf4j
@Component
public class ApplicationContextStartedListener implements ApplicationListener<ContextStartedEvent> {

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        System.out.println("***************ContextStartedEvent" + event.getApplicationContext().getDisplayName());
        if (event.getApplicationContext().getParent() == null){
            System.out.println("====================ContextStartedEvent====================");
        }
    }
}
