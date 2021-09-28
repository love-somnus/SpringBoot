package com.somnus.java8;

import java.util.function.Function;

/**
 * @author Somnus
 * @packageName com.somnus.java8
 * @title: FunctionTest
 * @description:  Function< T, R > 接收T对象，返回R对象 （接收参数，并返回结果）
 * @date 2019/11/7 16:09
 */
public class Java8Function {

    public static void main(String[] args) {
        Function<Integer, Integer> times2 = i -> i*2;
        Function<Integer, Integer> squared = i -> i*i;

        System.out.println(times2.apply(4));

        System.out.println(squared.apply(4));

        //32  先4×4然后16×2,先执行参数(即也是一个Function)的，再执行调用者(同样是一个Function)
        System.out.println(times2.compose(squared).apply(4));

        //64  先4×2,然后8×8,先执行调用者，再执行参数，和compose相反。
        System.out.println(times2.andThen(squared).apply(4));

        //16 4×4,返回当前正在执行的方法
        System.out.println(Function.identity().compose(squared).apply(4));

        System.out.println("加法结果：" + Java8Function.apply(3, value -> value + value));

        System.out.println("减法结果：" + Java8Function.apply(3, value -> value - 1));

        System.out.println("乘法结果：" + Java8Function.apply(3, value -> value * value));

        System.out.println("除法结果：" + Java8Function.apply(6, value -> value / 3));
    }

    static Integer apply(Integer num, Function<Integer, Integer> function) {
        return function.apply(num);
    }

}
