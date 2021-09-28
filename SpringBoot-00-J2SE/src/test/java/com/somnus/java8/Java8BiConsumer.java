package com.somnus.java8;

import java.util.function.BiConsumer;

/**
 * @author Somnus
 * @packageName com.somnus.java8
 * @title: FunctionTest
 * @description:  Function< T, R > 接收T对象，返回R对象 （接收参数，并返回结果）
 * @date 2019/11/7 16:09
 */
public class Java8BiConsumer {

    public static void main(String[] args) {
        BiConsumer<String, Integer> consumer = (key, value) -> System.out.println(String.format("out: %s - %s", key, value));
        consumer.accept("key",10);

        Java8BiConsumer.accept("key",10, (key, value) -> System.out.println(String.format("out: %s - %s", key, value)));
    }

    static void accept(String key, Integer value, BiConsumer<String, Integer> consumer) {
        consumer.accept(key, value);
    }

}
