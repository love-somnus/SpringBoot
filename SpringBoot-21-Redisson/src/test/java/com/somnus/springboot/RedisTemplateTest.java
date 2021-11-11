package com.somnus.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisTemplateTest {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void bloom(){
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

        // 故意取10000个不在过滤器里的值，看看有多少个会被认为在过滤器里
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

}
