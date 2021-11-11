package com.somnus.springboot.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @title: SpringApplicationListener
 * @description: TODO
 * @date 2020/4/20 16:12
 */
@Slf4j
@Component
public class ApplicationPreparedListener implements ApplicationListener<ApplicationPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        System.out.println("***************ApplicationPreparedEvent" + event.getApplicationContext().getDisplayName());
        if (event.getApplicationContext().getParent() == null){
            System.out.println("====================ApplicationPreparedEvent====================");
        }
    }
}
