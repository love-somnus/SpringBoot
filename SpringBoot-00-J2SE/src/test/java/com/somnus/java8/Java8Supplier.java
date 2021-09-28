package com.somnus.java8;

import org.junit.Test;

import java.util.function.Supplier;

/**
 * @author Kevin
 * @packageName com.somnus.java8
 * @title: Supplier1apply
 * @description: Supplier<T> 不接收参数，但返回一个新的或者独一的结果
 * @date 2020/5/14 18:16
 */
public class Java8Supplier {

    public static void main(String[] args) {
        Supplier<String> supplier = () -> {
            System.out.println("----------------------");
            return "######################";
        };
        System.out.println(supplier.get());

        System.out.println(Java8Supplier.get(() -> {
            System.out.println("----------------------");
            return "######################";
        }));
    }

    public static <T> T get(Supplier<? extends T> s) {
        return s.get();
    }

}
