package com.somnus.java8;

import org.junit.Test;

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
    }

    @Test
    public void applyTest(){

        Java8BiConsumer test = new Java8BiConsumer();

        test.accept("key",10, (key, value) -> System.out.println(String.format("out: %s - %s", key, value)));

    }

    /**
     * @param key
     * @param value
     * @return
     * @desc 使用JDK8 Function函数
     */
    private void accept(String key, Integer value, BiConsumer<String, Integer> consumer) {
        consumer.accept(key, value);
    }

}
