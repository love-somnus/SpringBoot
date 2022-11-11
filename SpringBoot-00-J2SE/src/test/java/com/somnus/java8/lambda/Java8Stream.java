package com.somnus.java8.lambda;

import org.junit.Test;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author Kevin
 * @packageName com.somnus.java8.lambda
 * @title: Java8Stream
 * @description: TODO
 * @date 2020/7/7 13:39
 */
public class Java8Stream {

    @Test
    public void of(){
        Stream.of("apple","banana","cherry","watermelon","orange").forEach(System.out::println);
    }

    @Test
    public void range(){
        IntStream.range(1, 9).forEach(item -> System.out.println(Thread.currentThread().getName()+ ">>"+ item));

        IntStream.rangeClosed(1, 9).forEach(item -> System.out.println(Thread.currentThread().getName()+ ">>"+ item));

        /*IntStream.range(0, 10).collect(Collectors.toList());*/

        IntStream.range(0, 10).boxed().collect(Collectors.toList());

        List<Fruit> fruits = IntStream.range(0, 10).mapToObj(Fruit::new).collect(Collectors.toList());
        System.out.println(fruits);
    }

    @Test
    public void generate(){
        LongStream.generate(() -> System.nanoTime() % 10000).limit(10).forEach(System.out::println);
    }

    @Test
    public void iterate(){
        //生成一个等差数列
        Stream.iterate(0, n -> n + 3).limit(10).forEach(System.out::println);

        List<String> fruits = List.of("apple","banana","cherry","watermelon","orange");
        Stream.iterate(0, i -> i + 1).limit(fruits.size()).forEach(i -> System.out.println(i + "---->" + fruits.get(i)));

        //斐波那契数列
        Stream.iterate(new int[]{0, 1}, n -> new int[]{n[1], n[0] + n[1]}).limit(10).map(n -> n[0]).forEach(System.out::println);

        System.out.println(Stream.iterate(new int[]{0, 1}, n -> new int[]{n[1], n[0] + n[1]}).limit(10).map(n -> n[0]).mapToInt(n -> n).sum());
    }

    @Test
    public void array(){
        String[] array = {"apple","banana","cherry","watermelon","orange"};

        Arrays.stream(array).forEach(System.out::println);

        System.out.println("-----------------------------------------------------------------");

        Pattern.compile("\\W").splitAsStream("apple banana cherry watermelon orange"). forEach(System.out::println);

        String params2 = "rose=1111&tulip =2222&tulip =3333&sunflower=44444";
        List<Fruit> list = Arrays.stream(params2.split("&")).map(value -> value.split("=")).map(value -> new Fruit(value[0], value[1])).collect(Collectors.toList());
        System.out.println(list);

        String fruits = list.stream().map(p -> p.getAlias() + "=" + p.getName()).reduce((p1, p2) -> p1 + "&" + p2).map(s -> "?" + s).orElse("");
        System.out.println(fruits);
        String fruits2 = list.stream().map(p -> p.getAlias() + "=" + p.getName()).collect(Collectors.joining("&"));
        System.out.println(fruits2);
    }

    @Test
    public void statistics(){
        List<Integer> nums = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        //统计函数
        IntSummaryStatistics stats = nums.stream().mapToInt(x -> x).summaryStatistics();
        System.out.println("最大值: " + stats.getMax());
        System.out.println("最小值: " + stats.getMin());
        System.out.println("平均值: " + stats.getAverage());
        System.out.println("总数: " + stats.getCount());
        System.out.println("总和: " + stats.getSum());
    }

}
