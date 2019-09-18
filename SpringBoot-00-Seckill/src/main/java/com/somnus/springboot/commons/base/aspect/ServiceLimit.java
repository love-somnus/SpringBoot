package com.somnus.springboot.commons.base.aspect;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: ServiceLimit
 * @Description: 自定义注解  限流
 * @author Somnus
 * @date 2018年8月31日
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented    
public  @interface ServiceLimit { 
     String description()  default "";
}
