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

    @Test
    public void guavaTest () {

        long star = System.currentTimeMillis();

        BloomFilter<Integer> filter = BloomFilter.create(Funnels.integerFunnel(),10000000,0.01);

        for(int i = 0;i < 10000000; i++) {
            filter.put(i);
        }

        System.out.println(filter.mightContain(1));
        System.out.println(filter.mightContain(100));
        System.out.println(filter.mightContain(1000));
        System.out.println(filter.mightContain(10000000));

        long end = System.currentTimeMillis();

        System.out.println("执行时间："+(end-star));
    }
}
