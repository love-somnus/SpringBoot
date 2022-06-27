package com.somnus.guava;

import com.google.common.util.concurrent.RateLimiter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author kevin.liu
 * @title: RateLimiterTest
 * @projectName SpringBoot
 * @description: TODO
 * @date 2022/1/24 19:11
 */
@Slf4j
public class RateLimiterTest {

    @Test
    @SneakyThrows
    public void limiter(){
        /* 最大流速 = 每10秒钟产生1个令牌*/
        RateLimiter rateLimiter = RateLimiter.create(1, 10, TimeUnit.SECONDS);

        ExecutorService executorService= Executors.newFixedThreadPool(10);
        for (int i=0;i<10;i++){
            executorService.submit(()->{
                log.info("{}-{}", Thread.currentThread().getName(), rateLimiter.tryAcquire());
            });
        }


        /** 主线程执行完毕后，如果子线程还没执行完成，就不会管的，测试的时候谨慎使用，main则不会*/
        try {
            Thread.sleep(5000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("$$$$$$$$$$$$$$$$$$");

    }
}
