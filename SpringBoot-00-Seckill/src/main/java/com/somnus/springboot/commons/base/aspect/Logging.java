package com.somnus.springboot.commons.base.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: Logger
 * @Description: 自定义注解  日志记录
 * @author Somnus
 * @date 2018年8月31日
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented    
public  @interface Logging { 
     String description()  default "";
}
