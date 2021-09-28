package com.somnus.java8.lambda;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
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
        IntStream.range(0, 10).forEach(item -> System.out.println(Thread.currentThread().getName()+ ">>"+ item));

        IntStream.rangeClosed(0, 10).forEach(item -> System.out.println(Thread.currentThread().getName()+ ">>"+ item));

        /*IntStream.range(0, 10).collect(Collectors.toList());*/

        IntStream.range(0, 10).boxed().collect(Collectors.toList());

        List<Fruit> fruits = IntStream.range(0, 10).mapToObj(i -> new Fruit(i)).collect(Collectors.toList());
    }

    @Test
    public void generate(){
        LongStream.generate(() -> System.nanoTime() % 10000).limit(10).forEach(System.out::println);
    }

    @Test
    public void iterate(){
        //生成一个等差数列
        Stream.iterate(0, n -> n + 3).limit(10).forEach(System.out::println);

        List<String> fruits = Lists.newArrayList("apple","banana","cherry","watermelon","orange");
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

        String params = "Chinese->English|Chinese->Franch";

        Map<String, String> map = Arrays.stream(params.split("[|]")).map(str -> str.split("->")).collect(Collectors.toMap(value -> value[1], value -> value[0],(u, v) -> v, LinkedHashMap::new));

        System.out.println(map);

        String params2 = "apk=cb&game=disgaea-tw&task=27&mobile=886953181966";

        Map<String, String> map2 = Arrays.stream(params2.split("&")).map(str -> str.split("=")).collect(Collectors.toMap(value -> value[0], value -> value[1],(u, v) -> v, LinkedHashMap::new));

        System.out.println(map2);
    }

    @Test
    public void statistics(){
        List<Integer> nums = Ints.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        //统计函数
        IntSummaryStatistics stats = nums.stream().mapToInt(x -> x).summaryStatistics();
        System.out.println("最大值: " + stats.getMax());
        System.out.println("最小值: " + stats.getMin());
        System.out.println("平均值: " + stats.getAverage());
        System.out.println("总数: " + stats.getCount());
        System.out.println("总和: " + stats.getSum());
    }

    @Test
    public void list2array(){
        List<Integer> nums = Ints.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);

        System.out.println(nums.stream().mapToInt(str -> str).toArray());
        System.out.println(nums.stream().mapToLong(str -> str).toArray());
        System.out.println(nums.stream().mapToDouble(str -> str).toArray());
    }

    @Test
    public void match(){
        List<Fruit> fruits = Lists.newArrayList(new Fruit("a", "apple"),new Fruit("b", "banana"),new Fruit("c", "cherry"));
        List<String> letters = Lists.newArrayList("a","w","d","f","c");
        Map<String, Fruit> fruitMap = fruits.stream().collect(Collectors.toMap(Fruit::getAlias, Function.identity()));
        List<String> f = letters.stream().filter(v -> fruitMap.get(v) != null).map(v -> fruitMap.get(v).getName()).collect(Collectors.toList());
        System.out.println(f);
    }

    public static void main(String[] args) {
        List<Fruit> fruits = Lists.newArrayList(new Fruit("a", "apple"),new Fruit("b", "banana"),new Fruit("c", "cherry"));
        List<String> letters = Lists.newArrayList("a","w","d","f","c");
        Map<String, Fruit> fruitMap = fruits.stream().collect(Collectors.toMap(Fruit::getAlias, Function.identity()));
        List<String> f = letters.stream().filter(v -> fruitMap.get(v) != null).map(v -> fruitMap.get(v).getName()).collect(Collectors.toList());
        System.out.println(f);
    }

}
