package com.somnus.springboot.commons.base.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Order(2)
@Slf4j
public class LogAspect {

    @Around("execution(public * *(..)) && @within(com.somnus.springboot.commons.base.aspect.Logging)")
    public Object logAspect(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        log.info(">>> request message:{}->方法名:{}", JSON.toJSONString(pjp.getArgs()[0]), signature.getName());
        Object result = pjp.proceed(); //Execute the method
        log.info("<<< response message:{}->方法名:{}", JSON.toJSONString(result), signature.getName());
        return result;
    }
    
}