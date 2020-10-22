package com.somnus.java8.lambda;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Kevin
 * @packageName com.somnus.java8.lambda
 * @title: Java8Collectors
 * @description: TODO
 * @date 2020/7/7 9:44
 */
public class Java8Collectors {

    @Test
    public void averaging(){
        List<Integer> nums = Ints.asList(3, 3, 2, 6, 3, 4, 3, 7, 8, 9, 2);
        System.out.println("平均值: " + nums.stream().collect(Collectors.averagingDouble(num -> num)));
        System.out.println("平均值: " + nums.stream().collect(Collectors.averagingInt(num -> num)));
        System.out.println("平均值: " + nums.stream().collect(Collectors.averagingLong(num -> num)));
    }

    @Test
    public void summing(){
        List<Integer> nums = Ints.asList(3, 3, 2, 6, 3, 4, 3, 7, 8, 9, 2);
        System.out.println("总和: " + nums.stream().collect(Collectors.summingInt(num -> num)));
        System.out.println("总和: " + nums.stream().collect(Collectors.summingDouble(num -> num)));
        System.out.println("总和: " + nums.stream().collect(Collectors.summingLong(num -> num)));
    }

    @Test
    public void summarizingInt(){
        List<Integer> nums = Ints.asList(3, 3, 2, 6, 3, 4, 3, 7, 8, 9, 2);
        //统计函数
        IntSummaryStatistics stats = nums.stream().collect(Collectors.summarizingInt(num -> num));
        System.out.println("最大值: " + stats.getMax());
        System.out.println("最小值: " + stats.getMin());
        System.out.println("平均值: " + stats.getAverage());
        System.out.println("总数: " + stats.getCount());
        System.out.println("总和: " + stats.getSum());

        List<String> numss = Lists.newArrayList("1", "2", "3", "4");
        //统计函数
        numss.stream().collect(Collectors.summarizingInt(Integer::parseInt));

        List<Fruit> fruits = Lists.newArrayList(new Fruit("apple", 5),new Fruit("banana", 6),new Fruit("apricot", 7));
        fruits.stream().collect(Collectors.summarizingInt(Fruit::getNum));

    }

    @Test
    public void maxBy(){
        List<Integer> nums = Ints.asList(3, 3, 2, 6, 3, 4, 3, 7, 8, 9, 2);
        System.out.println("最大值: " + nums.stream().collect(Collectors.maxBy(Comparator.naturalOrder())).get());
        System.out.println("最小值: " + nums.stream().collect(Collectors.minBy(Comparator.naturalOrder())).get());
    }

    @Test
    public void reducing(){
        List<Integer> nums = Ints.asList(3, 3, 2, 6, 3, 4, 3, 7, 8, 9, 2);
        System.out.println("总和: " + nums.stream().collect(Collectors.reducing(0, (a,b) -> a + b)));
        System.out.println("总和: " + nums.stream().collect(Collectors.reducing(0, Integer::sum)));
        System.out.println("最大值: " + nums.stream().collect(Collectors.reducing(0, Integer::max)));
        System.out.println("最小值: " + nums.stream().collect(Collectors.reducing(0, Integer::min)));

        List<Fruit> fruits = Lists.newArrayList(new Fruit("a", "apple"),new Fruit("b", "banana"),new Fruit("a", "almond"));
        //分组，保留组内key相同的最后一个
        Map<String, Optional<Fruit>> map = fruits.stream().collect(Collectors.groupingBy(Fruit::getAlias, Collectors.reducing((f,s) -> s)));
        System.out.println(map);

    }

    @Test//该收集器将输入元素累积到给定的收集器中，然后执行其他完成功能
    public void collectingAndThen(){
        List<Integer> nums = Ints.asList(1, 3, 5, 7, 9, 5);
        List<Integer> list2 = nums.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(num -> num))),
                ArrayList::new)
        );
        System.out.println(list2);

        double square = nums.stream().collect(Collectors.collectingAndThen(Collectors.averagingInt(num -> num), num -> num * num));
        System.out.println(square);
    }

    @Test
    public void joining(){
        List<String> fruits = Lists.newArrayList("apple","banana","cherry","watermelon","orange");
        System.out.println(fruits.stream().map(String::toUpperCase).collect(Collectors.joining()));
        System.out.println(fruits.stream().map(String::toUpperCase).collect(Collectors.joining(",")));
        System.out.println(fruits.stream().map(String::toUpperCase).collect(Collectors.joining(",", "(", ")")));
    }

    @Test
    public void groupingBy(){
        List<Fruit> fruits = Lists.newArrayList(new Fruit("a", "apple"),new Fruit("b", "banana"),new Fruit("a", "apricot"));

        //分组
        Map<String, List<Fruit>> groupMap = fruits.stream().collect(Collectors.groupingBy(Fruit::getName));
        System.out.println(groupMap);

        //计数
        Map<String, Long> map = fruits.stream().collect(Collectors.groupingBy(Fruit::getAlias, Collectors.counting()));
        System.out.println(map);

        //上述代码根据name将list分组，如果name是唯一的，那么上述代码就会显得啰嗦。我们需要知道，Guava补JDK之不足，现在改Guava一显身手了
        List<Fruit> ulist = Lists.newArrayList(new Fruit("a", "apple"),new Fruit("b", "banana"),new Fruit("c", "cherry"));
        Map<String, Fruit> guava = Maps.uniqueIndex(ulist, Fruit::getName);
        System.out.println(guava);
    }

    @Test
    public void counting(){
        List<String> fruits = Lists.newArrayList("apple","banana","apple","watermelon","orange");
        System.out.println(fruits.stream().collect(Collectors.counting()));
    }

    @Test
    public void comparing(){
        Map<String, String> fruits = ImmutableMap.of("b", "banana", "a", "apple", "c", "cherry");
        List<String> list = fruits.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey())).map(e -> e.getValue()).collect(Collectors.toList());
        System.out.println(list);
        List<String> list2 = fruits.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).map(e -> e.getValue()).collect(Collectors.toList());
        System.out.println(list2);
        List<String> list3 = fruits.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(e -> e.getValue()).collect(Collectors.toList());
        System.out.println(list3);
        List<String> list4 = fruits.entrySet().stream().sorted(Map.Entry.<String, String>comparingByValue().reversed()).map(e -> e.getValue()).collect(Collectors.toList());
        System.out.println(list4);
    }

    @Test
    public void toMap(){
        List<Fruit> fruits = Lists.newArrayList(new Fruit("a", "apple"),new Fruit("b", "banana"),new Fruit("c", "cherry"));
        Map<String, String> map = fruits.stream().collect(Collectors.toMap(Fruit::getAlias, Fruit::getName));
        System.out.println("①" + map);

        System.out.println(map.entrySet().stream().collect(Collectors.toMap(e->e.getKey()+"@",e->e.getValue())));

        Map<String, Fruit> map2 = fruits.stream().collect(Collectors.toMap(Fruit::getAlias, Function.identity()));
        System.out.println("②" + map2);

        List<String> fruitss = Lists.newArrayList("apple","banana","cherry","watermelon","orange");
        Map<String, Integer> map3 = fruitss.stream().collect(Collectors.toMap(Function.identity(), String::length));
        System.out.println("③" + map3);

        //解决Key冲突
        fruits = Lists.newArrayList(new Fruit("a", "apple"),new Fruit("b", "banana"),new Fruit("a", "apricot"));

        /** (f, s) -> f 使用旧的值  (f, s) -> s  使用新的值替换旧的值  (f, s) -> f + s 对新值和旧值进行处理，比如返回新值与旧值的和*/
        Map<String, String> map6 = fruits.stream().collect(Collectors.toMap(Fruit::getAlias, Fruit::getName, (existing, replacement) -> existing));
        System.out.println("⑥" + map6);
        //如果我们不想使用默认的返回类型HashMap，可以通过toMap方法的最后一个参数来进行自定义返回类型：
        Map<String, String> map7 = fruits.stream().collect(Collectors.toMap(Fruit::getAlias, Fruit::getName, (existing, replacement) -> replacement, TreeMap::new));
        System.out.println("⑦" + map7);

        Map<String, Fruit> map8 = fruits.stream().collect(Collectors.toMap(Fruit::getAlias, v -> v, (existing, replacement) -> existing));
        System.out.println("⑧" + map8);

        String[] array = {"apple","banana","cherry","watermelon","orange"};

        Stream.iterate(0, i -> i).limit(array.length).collect(Collectors.toMap(Function.identity(),i -> array[i]));
    }

    @Test
    public void toConcurrentMap(){
        List<String> fruits = Lists.newArrayList("apple","banana","apple","watermelon","orange");
        //都需去重
        ConcurrentMap<String, Integer> map = fruits.stream().distinct().collect(Collectors.toConcurrentMap(Function.identity(), String::length));
        System.out.println(map);

        ConcurrentMap<String, Integer> map2 = fruits.stream().collect(Collectors.toConcurrentMap(Function.identity(), String::length, (existing, replacement) -> existing));
        System.out.println(map2);

        ConcurrentMap<String, Integer> map3 = fruits.stream().collect(Collectors.toConcurrentMap(Function.identity(), String::length, BinaryOperator.maxBy(Comparator.naturalOrder())));
        System.out.println(map3);
    }

    @Test
    public void toSet(){
        List<String> fruits = Lists.newArrayList("apple","banana","apple","watermelon","orange");
        Set<String> set = fruits.stream().collect(Collectors.toSet());
        System.out.println(set);
    }

    @Test//返回一个收集器，它将输入元素累积到一个集合中
    public void toCollection(){
        List<String> fruits = Lists.newArrayList("apple","banana","apple","watermelon","orange");

        Set<String> set = fruits.stream().collect(Collectors.toCollection(TreeSet::new));
        System.out.println(set);

        List<String> fruitss = set.stream().collect(Collectors.toCollection(LinkedList::new));
        System.out.println(fruitss);
    }

    @Test//它根据Predicate对输入元素进行分区，并将它们组织成Map <Boolean，List <T >>
    public void partitioningBy(){
        List<String> fruits = Lists.newArrayList("apple","banana","chry","watermelon","orange");

        Map<Boolean, List<String>> map = fruits.stream().collect(Collectors.partitioningBy(s -> s.length() > 5));
        System.out.println(map);

        map.forEach((key, value) ->{
            System.out.println(key);
            System.out.println(value);
        });

        Map<Boolean, Long> m = fruits.stream().collect(Collectors.partitioningBy(s -> s.length() > 5, Collectors.counting()));
        System.out.println(m);

        Map<Boolean, Optional<String>> map2 = fruits.stream().collect(Collectors.partitioningBy(s -> s.length() > 5, Collectors.minBy(Comparator.comparing(String::length))));
        System.out.println(map2);

        map2 = fruits.stream().collect(Collectors.partitioningBy(s -> s.length() > 5, Collectors.reducing(BinaryOperator.minBy(Comparator.comparing(String::length)))));
        System.out.println(map2);
    }

    @Test
    public void mapping(){
        List<String> fruits = Lists.newArrayList("apple","banana","apple","watermelon","orange");

        System.out.println(fruits.stream().collect(Collectors.mapping(String::toUpperCase, Collectors.toList())));

        System.out.println(fruits.stream().collect(Collectors.mapping(String::toUpperCase, Collectors.joining(","))));

        List<Fruit> fruitss = Lists.newArrayList(new Fruit("a", "apple"),new Fruit("b", "banana"),new Fruit("a", "abc"));

        System.out.println(fruitss.stream().collect(Collectors.groupingBy(Fruit::getAlias, Collectors.mapping(Fruit::getName, Collectors.joining(",")))));

        System.out.println(fruitss.stream().collect(Collectors.groupingBy(Fruit::getAlias, Collectors.mapping(Fruit::getName, Collectors.toList()))));
    }
}
