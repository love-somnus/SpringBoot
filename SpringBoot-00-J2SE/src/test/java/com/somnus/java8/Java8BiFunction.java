package com.somnus.java8;

import org.junit.Test;

import java.util.function.BiFunction;

/**
 * @author Somnus
 * @packageName com.somnus.java8
 * @title: FunctionTest
 * @description:  Function< T, R > 接收T对象，返回R对象 （接收参数，并返回结果）
 * @date 2019/11/7 16:09
 */
public class Java8BiFunction {

    public static void main(String[] args) {
        BiFunction<Integer, Integer, Integer> function = (value1,value2) -> value1 + value2;
        System.out.println("加法结果：" + function.apply(10, 10));

        // 使用andThen场景, 从左向右处理, 这里就是 (10 + 10) * 20 = 400
        System.out.println("Function compose 结果：" +  function.andThen(value -> value * value).apply(10, 10));
    }

    @Test
    public void applyTest(){

        Java8BiFunction test = new Java8BiFunction();

        // Function函数的使用
        System.out.println("加法结果：" + test.apply(10, 10, (value1,value2) -> value1 + value2));
    }

    /**
     * @param num
     * @param num2
     * @return
     * @desc 使用JDK8 Function函数
     */
    private Integer apply(Integer num, Integer num2, BiFunction<Integer, Integer, Integer> function) {
        return function.apply(num, num2);
    }

}
