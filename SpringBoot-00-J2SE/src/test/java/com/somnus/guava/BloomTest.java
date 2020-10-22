package com.somnus.guava;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Test;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2019/1/2 13:08
 */
public class BloomTest {

    /**
     * 只适合单机，分布式环境下使用redis布隆过滤器（需4.0+版本）
     */
    @Test
    public void guavaTest () {

        long star = System.currentTimeMillis();

        int total = 10000000;

        BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(), total,0.001);

        for(int i = 0;i < total; i++) {
            filter.put(i);
        }

        for(int i = 0; i < total; i++){
            if(!filter.mightContain(i)){
                //不会打印，因为不存在的情况不会出现误判(能保证一定不存在)
                System.out.println("不存在的误判" + i);
            }
        }

        // 故意取10000个不在过滤器里的值，看看有多少个会被认为在过滤器里
        int count = 0;
        for (int i = total; i < total + 10000; i++) {
            if (filter.mightContain(i)) {
                count++;
            }
        }
        System.out.println("误伤的数量：" + count);

        long end = System.currentTimeMillis();

        System.out.println("执行时间："+(end-star));
    }
}
