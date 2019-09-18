package com.somnus.springboot.commons.base.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.RateLimiter;

/**
 * @ClassName: LimitAspect
 * @Description: 限流 AOP
 * @author Somnus
 * @date 2018年8月31日
 */
@Component
@Scope
@Aspect
public class LimitAspect {
    ////每秒只发出5个令牌，此处是单进程服务的限流,内部采用令牌捅算法实现
    private static   RateLimiter rateLimiter = RateLimiter.create(5.0);

    //Service层切点  限流
    @Pointcut("@annotation(com.somnus.springboot.commons.base.aspect.ServiceLimit)")
    public void ServiceAspect() {

    }

    @Around("ServiceAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) { 
        Boolean flag = rateLimiter.tryAcquire();
        Object obj = null;
        try {
            if(flag){
                obj = joinPoint.proceed();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return obj;
    } 
}
