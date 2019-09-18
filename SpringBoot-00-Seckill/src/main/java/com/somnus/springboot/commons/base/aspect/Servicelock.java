package com.somnus.springboot.commons.base.aspect;

import java.lang.annotation.*; 

/**
 * @ClassName: Servicelock
 * @Description: 自定义注解 同步锁
 * @author Somnus
 * @date 2018年8月31日
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented    
public  @interface Servicelock { 
     String description()  default "";
}
