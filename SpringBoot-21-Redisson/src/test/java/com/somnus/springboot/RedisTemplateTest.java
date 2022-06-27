package com.somnus.springboot;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisTemplateTest {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void bloom(){
        Long serialnumber = redissonClient.getAtomicLong("serial-number").addAndGet(1);
        System.out.println(serialnumber);
        long star = System.currentTimeMillis();

        int total = 10000;

        RBloomFilter<Integer> bloomFilter = redissonClient.getBloomFilter("bloom");
        bloomFilter.tryInit(10000000, 0.1);

        for(int i = 0; i < total; i++) {
            bloomFilter.add(i);
        }

        bloomFilter.removeListener(1);

        System.out.println(bloomFilter.contains(1));
        System.out.println(bloomFilter.contains(2));

        for(int i = 0; i < 1000; i++){
            if(!bloomFilter.contains(i)){
                //不会打印，因为不存在的情况不会出现误判(能保证一定不存在)
                System.out.println("不存在的误判" + i);
            }
        }

        // 存入过过滤器的值，一定能判定出存在或者不存在；未存入过过滤器的值，可能会被误判存入过
        int count = 0;
        for (int i = total; i < total + 10000; i++) {
            if (bloomFilter.contains(i)) {
                count++;
            }
        }
        System.out.println("误伤的数量：" + count);

        long end = System.currentTimeMillis();

        System.out.println("执行时间："+(end-star));
    }

    @Test
    @SneakyThrows
    public void limiter(){
        /* 设置访问速率，rate为访问数，rateInterval为单位时间 */
        RRateLimiter rateLimiter = redissonClient.getRateLimiter("limiter");
        /* 最大流速 = 每10秒钟产生1个令牌*/
        rateLimiter.trySetRate(RateType.OVERALL, 1, 10, RateIntervalUnit.SECONDS);

        ExecutorService executorService= Executors.newFixedThreadPool(10);
        for (int i=0;i<10;i++){
            executorService.submit(()->{
                System.out.println(rateLimiter.availablePermits());
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
