package com.somnus.java8.lambda;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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

        String params = "apple=1111&banana=2222&pear=3333&grape=44444";
        Map<String, String> map = Arrays.stream(params.split("&")).map(str -> str.split("=")).collect(Collectors.toMap(value -> value[0], value -> value[1], (u, v) -> v, LinkedHashMap::new));
        System.out.println(map);

        String str = map.entrySet().stream().map(p -> p.getKey() + "=" + p.getValue()).reduce((p1, p2) -> p1 + "&" + p2).map(s -> "?" + s).orElse("");
        System.out.println(str);
        String str2 = map.entrySet().stream().map(p -> p.getKey() + "=" + p.getValue()).collect(Collectors.joining("&"));
        System.out.println(str2);

        String params2 = "rose=1111&tulip =2222&tulip =3333&sunflower =44444";
        List<Fruit> list = Arrays.stream(params2.split("&")).map(value -> value.split("=")).map(value -> new Fruit(value[0], value[1])).collect(Collectors.toList());
        System.out.println(list);

        String fruits = list.stream().map(p -> p.getAlias() + "=" + p.getName()).reduce((p1, p2) -> p1 + "&" + p2).map(s -> "?" + s).orElse("");
        System.out.println(fruits);
        String fruits2 = list.stream().map(p -> p.getAlias() + "=" + p.getName()).collect(Collectors.joining("&"));
        System.out.println(fruits2);
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
        List<String> nums = Lists.newArrayList("1", "2", "3", "4");
        //List<String> 转 Integer[ ]
        System.out.println(Arrays.toString(nums.stream().map(Integer::parseInt).toArray()));
        System.out.println(Arrays.toString(nums.stream().map(Long::parseLong).toArray()));
        System.out.println(Arrays.toString(nums.stream().map(Double::parseDouble).toArray()));
        System.out.println(Arrays.toString(nums.stream().map(Integer::parseInt).toArray(Integer[]::new)));
    }

    @Test
    public void array2list(){

        /* String[ ] 转 List<String> */
        List<String> fruits = Stream.of("apple", "banana").collect(Collectors.toList());
        System.out.println(fruits);
        List<String> flowers = Stream.of(new String[]{"rose", "tulip"}).collect(Collectors.toList());
        System.out.println(flowers);

        //String[ ] 转 Integer [ ]
        String[] strArry = new String[]{"5", "6", "1", "4", "9"};
        Integer[] integerArry = Arrays.stream(strArry).map(Integer::parseInt).toArray(Integer[]::new);
        System.out.println(integerArry);

        //int [ ] 转 List<Integer>
        int[] intArry = new int[]{5,6,1,4,9};
        List<Integer> integerList = Arrays.stream(intArry).boxed().collect(Collectors.toList());
        System.out.println(integerList);

        //int [ ] 转 Integer [ ]
        Integer[] integerArry2 = Arrays.stream(intArry).boxed().toArray(Integer[]::new);
        System.out.println(integerArry2);

        /* Arrays.asList(strArray)返回值是仍然是一个可变的集合，但是返回值是其内部类，不具有add方法，可以通过set方法进行增加值，默认长度是10 */
        System.out.println(Arrays.asList("apple", "banana"));
        /* 返回的是不可变的集合，但是这个长度的集合只有1，可以减少内存空间。但是返回的值依然是Collections的内部实现类，同样没有add的方法，调用add，set方法会报错*/
        System.out.println(Collections.singletonList("rose"));
    }

    @Test
    public void set2array(){
        Set<String> set = Sets.newHashSet("1", "2", "3", "4");
        String[] array = set.toArray(new String[0]);
        System.out.println(array);
        String[] array2 = set.stream().toArray(String[]::new);
        System.out.println(array2);
    }
    @Test
    public void array2set(){
        String[] array = new String[]{"5", "6", "1", "4", "9"};
        Set<String> set = new HashSet<>(Arrays.asList(array));
        System.out.println(set);
        Set<String> set2 = Stream.of(array).collect(Collectors.toSet());
        System.out.println(set2);
    }

    @Test
    public void set2list(){
        Set<String> set = Sets.newHashSet("1", "2", "3", "4");
        List<String> list = new ArrayList<>(set);
        System.out.println(list);
        List<String> list2 = Stream.of(set.toArray(new String[0])).collect(Collectors.toList());
        System.out.println(list2);
    }
    @Test
    public void list2set(){
        List<String> list = Lists.newArrayList("1", "2", "3", "4");
        Set<String> set = new HashSet<>(list);
        System.out.println(set);
        Set<String> set2 = list.stream().collect(Collectors.toSet());
        System.out.println(set2);
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
