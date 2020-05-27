package com.somnus.java8;

import org.junit.Test;

import java.util.function.Predicate;

/**
 * @author Kevin
 * @packageName com.somnus.java8
 * @title: Predicate1
 * @description: Predicate<T> 接收T对象并返回boolean
 * @date 2020/5/14 19:01
 */
public class Java8Predicate {

    public static void main(String[] args) {
        Predicate<Integer> p = value -> value > 6;
        System.out.println(p.test(7));

        /** 取反操作 为true则返回false*/
        System.out.println(p.negate().test(7));

        /** 且操作 同时满足两个 才返回true*/
        System.out.println(p.and(value -> value < 100).test(7));

        /** 或操作 满足任意一个 就返回true*/
        System.out.println(p.or(value -> value < 1).test(5));
    }

    @Test
    public void test(){
        Java8Predicate predicate1test = new Java8Predicate();
        predicate1test.test(7, value -> value > 6);
    }

    public boolean test(Integer num, Predicate<Integer> p){
        return p.test(num);
    }
}
