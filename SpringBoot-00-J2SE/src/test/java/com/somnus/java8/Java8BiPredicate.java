package com.somnus.java8;

import java.util.function.BiPredicate;

/**
 * @author Kevin
 * @packageName com.somnus.java8
 * @title: Predicate1
 * @description: Predicate<T> 接收T对象并返回boolean
 * @date 2020/5/14 19:01
 */
public class Java8BiPredicate {

    public static void main(String[] args) {

        BiPredicate<String, Integer> filter = (x, y) -> x.length() == y;

        System.out.println(filter.test("cattle", 6));

        System.out.println(filter.test("java", 8));

        System.out.println(Java8BiPredicate.test("admin",7, (name, age) -> name.startsWith("a") && age > 5));

    }

    public static boolean test(String name, Integer age, BiPredicate<String, Integer> p){
        return p.test(name, age);
    }

    /** 同时满足两个 才返回true*/
    public static boolean test(String name, Integer age, BiPredicate<String, Integer> p1, BiPredicate<String, Integer> p2){
        return p1.and(p2).test(name, age);
    }

    /** 满足任意一个 就返回true*/
    public static boolean test2(String name, Integer age, BiPredicate<String, Integer> p1, BiPredicate<String, Integer> p2){
        return p1.or(p2).test(name, age);
    }
}
