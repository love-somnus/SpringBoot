package com.somnus.java8;

import java.util.function.UnaryOperator;

/**
 * @author Kevin
 * @packageName com.somnus.java8
 * @title: Java8UnaryOperator
 * @description: UnaryOperator继承了Function，与Function作用相同
 *               不过UnaryOperator，限定了传入类型和返回类型必需相同
 * @date 2020/5/15 10:10
 */
public class Java8UnaryOperator {

    public static void main(String[] args) {
        UnaryOperator<Integer> unary = value -> value * value * value;
        System.out.println(unary.apply(10));

        System.out.println(UnaryOperator.identity());
    }
}
