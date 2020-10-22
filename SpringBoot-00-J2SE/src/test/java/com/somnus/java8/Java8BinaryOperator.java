package com.somnus.java8;

import java.util.function.BinaryOperator;

/**
 * @author Kevin
 * @packageName com.somnus.java8
 * @title: Java8BinaryOperator
 * @description: UnaryOperator继承了BiFunction，与BiFunction作用相同
 *  不过BinaryOperator，限定了传入类型和返回类型必需相同
 * @date 2020/5/15 10:10
 */
public class Java8BinaryOperator {

    public static void main(String[] args) {
        BinaryOperator<Integer> binary = (a,b) -> a * b;
        System.out.println(binary.apply(10,20));
    }
}
