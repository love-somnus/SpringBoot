package com.somnus.java8;

import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Kevin
 * @packageName com.somnus.java8
 * @title: Consumer1accpect
 * @description: Consumer<T>  接收参数，无返回结果
 * @date 2020/5/14 18:38
 */
public class Java8Consumer {

    public static void main(String[] args) {
        Consumer<String> c = value -> System.out.println(value);
        c.accept("ddd");

        Consumer<Integer> c2 = value -> {
            System.out.println(value);
            System.out.println(value * value);
        };
        c2.accept(10);

        Consumer<Integer> c3 = value -> {
            System.out.println("-----------");
            System.out.println(value * value);
        };
        Consumer<Integer> c4 = value -> {
            System.out.println("***********");
            System.out.println(value * value);
        };
        c3.andThen(c4).accept(10);
    }

    @Test
    public void test(){
        Java8Consumer cc= new Java8Consumer();

        cc.accept(10 ,value -> System.out.println(value));

        cc.accept(10 ,value -> {
            System.out.println(value);
            System.out.println(value * value);
        });
    }

    void accept(Integer num, Consumer<Integer> consumer){
        consumer.accept(num);
    }

    void accept(List<Integer> nums, Consumer<Integer> consumer){

        Integer sum = nums.stream().collect(Collectors.summingInt(num -> num));

        consumer.accept(sum);
    }

    @Test
    public void test2(){
        Java8Consumer cc= new Java8Consumer();

        cc.accept(Ints.asList(1, 2, 3) , value -> System.out.println(value));
    }

}
