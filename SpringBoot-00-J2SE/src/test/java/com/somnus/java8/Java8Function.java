package com.somnus.java8;

import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Somnus
 * @packageName com.somnus.java8
 * @title: FunctionTest
 * @description:  Function< T, R > 接收T对象，返回R对象 （接收参数，并返回结果）
 * @date 2019/11/7 16:09
 */
public class Java8Function {

    public static void main(String[] args) {
        Function<Integer, Integer> function = value -> value + value;
        System.out.println("加法结果：" + function.apply(10));

        // 使用compose场景, 从右向左处理, 这里就是 (10 * 10) + 100 = 200
        /*System.out.println("Function compose 结果：" +  function.compose(value -> value).apply(10));*/

        // 使用andThen场景, 从左向右处理, 这里就是 (10 + 10) * 20 = 400
        System.out.println("Function compose 结果：" +  function.andThen(value -> value * value).apply(10));
    }

    @Test
    public void applyTest(){

        Java8Function test = new Java8Function();

        // Function函数的使用
        System.out.println("加法结果：" + test.apply(3, value -> value + value));

        System.out.println("减法结果：" + test.apply(3, value -> value - 1));

        System.out.println("乘法结果：" + test.apply(3, value -> value * value));

        System.out.println("除法结果：" + test.apply(6, value -> value / 3));
    }

    /**
     * @param num
     * @param function
     * @return
     * @desc 使用JDK8 Function函数
     */
    Integer apply(Integer num, Function<Integer, Integer> function) {
        return function.apply(num);
    }


    String apply(List<Integer> nums, Function<Integer, String> function){

        Integer sum = nums.stream().collect(Collectors.summingInt(num -> num));

        return function.apply(sum);
    }

    @Test
    public void applyTest2(){

        Java8Function test = new Java8Function();

        // Function函数的使用
        System.out.println(test.apply(Ints.asList(1, 2, 3), value -> value.toString()));

    }

}
