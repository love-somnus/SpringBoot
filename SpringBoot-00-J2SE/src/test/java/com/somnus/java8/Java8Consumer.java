package com.somnus.java8;

import java.util.function.Consumer;

/**
 * @author Kevin
 * @packageName com.somnus.java8
 * @title: Consumer1accpect
 * @description: Consumer<T>  接收参数，无返回结果
 * @date 2020/5/14 18:38
 */
public class Java8Consumer {

    public static void main(String[] args) {
        Consumer<Integer> times2 = value -> System.out.println(value * 2);
        times2.accept(10);

        Consumer<Integer> squared = value -> System.out.println(value * value);
        squared.accept(10);

        //先执行调用者times2，再执行参数squared
        times2.andThen(squared).accept(10);

        Java8Consumer.accept(10 , System.out::println);

        Java8Consumer.accept(10 ,value -> System.out.println(value * value));
    }

    static void accept(Integer num, Consumer<Integer> consumer){
        consumer.accept(num);
    }

}
