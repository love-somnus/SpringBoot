package com.somnus.springboot;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2018/12/5 20:14
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(ApplicationContextHolder.applicationContext == null) {
            ApplicationContextHolder.applicationContext = applicationContext;
        }
    }

    /** 获取applicationContext */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /** 通过name获取 Bean. */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    /** 通过class获取Bean. */
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    /** 通过name,以及Clazz返回指定的Bean */
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

}