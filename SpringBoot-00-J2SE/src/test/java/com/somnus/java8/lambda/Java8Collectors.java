package com.somnus.java8.lambda;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        List<Integer> nums = List.of(3, 3, 2, 6, 3, 4, 3, 7, 8, 9, 2);
        System.out.println("平均值: " + nums.stream().collect(Collectors.averagingDouble(num -> num)));
        System.out.println("平均值: " + nums.stream().collect(Collectors.averagingInt(num -> num)));
        System.out.println("平均值: " + nums.stream().collect(Collectors.averagingLong(num -> num)));
    }

    @Test
    public void summing(){
        List<Integer> nums = List.of(3, 3, 2, 6, 3, 4, 3, 7, 8, 9, 2);
        System.out.println("总和: " + nums.stream().mapToInt(num -> num).sum());
        System.out.println("总和: " + nums.stream().mapToDouble(num -> num).sum());
        System.out.println("总和: " + nums.stream().mapToLong(num -> num).sum());

        List<Fruit> fruits = List.of(new Fruit("a", 2),new Fruit("b", 4),new Fruit("a", 1));
        System.out.println(fruits.stream().map(Fruit::getNum).mapToInt(num -> num).sum());
    }

    @Test
    public void summarizingInt(){
        List<Integer> nums = List.of(3, 3, 2, 6, 3, 4, 3, 7, 8, 9, 2);
        //统计函数
        IntSummaryStatistics stats = nums.stream().collect(Collectors.summarizingInt(num -> num));
        System.out.println("最大值: " + stats.getMax());
        System.out.println("最小值: " + stats.getMin());
        System.out.println("平均值: " + stats.getAverage());
        System.out.println("总数: " + stats.getCount());
        System.out.println("总和: " + stats.getSum());

        List<String> numss = List.of("1", "2", "3", "4");
        //统计函数
        IntSummaryStatistics s = numss.stream().collect(Collectors.summarizingInt(Integer::parseInt));

        List<Fruit> fruits = List.of(new Fruit("apple", 5),new Fruit("banana", 6),new Fruit("apricot", 7));
        fruits.stream().collect(Collectors.summarizingInt(Fruit::getNum));

    }

    @Test
    public void maxBy(){
        List<Integer> nums = List.of(3, 3, 2, 6, 3, 4, 3, 7, 8, 9, 2);
        System.out.println("最大值: " + nums.stream().max(Comparator.naturalOrder()).get());
        System.out.println("最小值: " + nums.stream().min(Comparator.naturalOrder()).get());
    }

    @Test
    public void reducing(){
        List<Integer> nums = List.of(3, 3, 2, 6, 3, 4, 3, 7, 8, 9, 2);
        System.out.println("总和: " + nums.stream().reduce(0, (a, b) -> a + b));
        System.out.println("总和: " + nums.stream().reduce(0, Integer::sum));
        System.out.println("最大值: " + nums.stream().reduce(0, Integer::max));
        System.out.println("最小值: " + nums.stream().reduce(0, Integer::min));

        List<Fruit> fruits = List.of(new Fruit("a", "apple"),new Fruit("b", "banana"),new Fruit("a", "almond"));
        //分组，保留组内key相同的最后一个
        Map<String, Optional<Fruit>> map = fruits.stream().collect(Collectors.groupingBy(Fruit::getAlias, Collectors.reducing((f,s) -> s)));
        System.out.println(map);
    }

    @Test//该收集器将输入元素累积到给定的收集器中，然后执行其他完成功能
    public void collectingAndThen(){
        List<Integer> nums = List.of(1, 3, 5, 7, 9, 5);
        List<Integer> list2 = nums.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(num -> num))),
                ArrayList::new)
        );
        System.out.println(list2);

        double square = nums.stream().collect(Collectors.collectingAndThen(Collectors.averagingInt(num -> num), num -> num * num));
        System.out.println(square);

        List<Fruit> fruits = List.of(new Fruit("a", "apple"),new Fruit("a", "apple"),new Fruit("b", "apple"));
        List<Fruit> uniqueByName = fruits.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Fruit::getName))), ArrayList::new)
        );
        System.out.println(uniqueByName);

        List<Fruit> uniqueByNameAndAlia = fruits.stream().collect(
                Collectors. collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getName() + ";" + o.getAlias()))), ArrayList::new)
        );
        System.out.println(uniqueByNameAndAlia);
    }

    @Test
    public void joining(){
        List<String> fruits = List.of("apple","banana","cherry","watermelon","orange");
        System.out.println(fruits.stream().map(String::toUpperCase).collect(Collectors.joining()));
        System.out.println(fruits.stream().map(String::toUpperCase).collect(Collectors.joining(",")));
        System.out.println(fruits.stream().map(String::toUpperCase).collect(Collectors.joining(",", "(", ")")));
    }

    @Test
    public void groupingBy(){
        List<Fruit> fruits = List.of(new Fruit("a", "apple"),new Fruit("b", "banana"),new Fruit("a", "apricot"));

        //分组
        Map<String, List<Fruit>> groupMap = fruits.stream().collect(Collectors.groupingBy(Fruit::getName));
        System.out.println(groupMap);

        List<Fruit> fruits2 = List.of(new Fruit("a", 1),new Fruit("b", 2),new Fruit("a", 3));
        Map<String, IntSummaryStatistics> newLog =
                fruits2.stream().collect(Collectors.groupingBy(Fruit::getName, Collectors.summarizingInt(Fruit::getNum)));
        System.out.println(newLog);

        //计数
        Map<String, Long> map = fruits.stream().collect(Collectors.groupingBy(Fruit::getAlias, Collectors.counting()));
        System.out.println(map);
        //List<String>统计各字符串出现的次数
        List<String> items = Arrays.asList("apple", "apple", "orange", "orange", "orange", "blueberry", "peach", "peach", "peach", "peach");
        Map<String, Long> result = items.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(result);

        //上述代码根据name将list分组，如果name是唯一的，那么上述代码就会显得啰嗦。我们需要知道，Guava补JDK之不足，现在改Guava一显身手了
        List<Fruit> ulist = List.of(new Fruit("a", "apple"),new Fruit("b", "banana"),new Fruit("c", "cherry"));
        Map<String, Fruit> guava = Maps.uniqueIndex(ulist, Fruit::getName);
        System.out.println(guava);
    }

    @Test
    public void comparing(){
        Map<String, String> fruits = Map.of("b", "banana", "a", "apple", "c", "cherry");
        //map转list
        List<String> list = fruits.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).map(Map.Entry::getValue).collect(Collectors.toList());
        System.out.println(list);
        List<String> list2 = fruits.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(Map.Entry::getValue).collect(Collectors.toList());
        System.out.println(list2);
        List<String> list3 = fruits.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(Map.Entry::getValue).collect(Collectors.toList());
        System.out.println(list3);
        List<String> list4 = fruits.entrySet().stream().sorted(Map.Entry.<String, String>comparingByValue().reversed()).map(Map.Entry::getValue).collect(Collectors.toList());
        System.out.println(list4);

        //map排序值里面的对象的属性,再输出map
        Map<String, Fruit> fruitss = Map.of("a", new Fruit("apple", 35), "b", new Fruit("banana", 56), "c", new Fruit("cherry", 2));
        Map<String, Fruit> fruitMap = fruitss.entrySet().stream().sorted(Comparator.comparing(e -> e.getValue().getNum(), Comparator.reverseOrder())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldVal, newVal) -> oldVal, LinkedHashMap::new));
        System.out.println( fruitMap  );
    }

    @Test
    public void toMap(){
        List<Fruit> fruits = List.of(new Fruit("a", "apple"),new Fruit("b", "banana"),new Fruit("c", "cherry"));
        Map<String, String> map = fruits.stream().peek(v -> {
            System.out.println("#############" + v.getAlias());
        }).collect(Collectors.toMap(Fruit::getAlias, Fruit::getName));
        System.out.println("①list转map" + map);

        System.out.println( "①map转map" + map.entrySet().stream().collect(Collectors.toMap(e->e.getKey()+"@", e->e.getValue())) );
        System.out.println( "①map转map" + map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)) );

        Map<String, Fruit> map2 = fruits.stream().collect(Collectors.toMap(Fruit::getAlias, Function.identity()));
        System.out.println("②list转map" + map2);

        List<String> fruitss = List.of("apple","banana","cherry","watermelon","orange");
        Map<String, Integer> map3 = fruitss.stream().collect(Collectors.toMap(Function.identity(), String::length));
        System.out.println("③list转map" + map3);

        //解决Key冲突
        fruits = List.of(new Fruit("a", "apple"),new Fruit("b", "banana"),new Fruit("a", "apricot"));

        /** (f, s) -> f 使用旧的值  (f, s) -> s  使用新的值替换旧的值  (f, s) -> f + s 对新值和旧值进行处理，比如返回新值与旧值的和*/
        Map<String, String> map6 = fruits.stream().collect(Collectors.toMap(Fruit::getAlias, Fruit::getName, (existing, replacement) -> existing));
        System.out.println("⑥" + map6);
        //如果我们不想使用默认的返回类型HashMap，可以通过toMap方法的最后一个参数来进行自定义返回类型：
        Map<String, String> map7 = fruits.stream().collect(Collectors.toMap(Fruit::getAlias, Fruit::getName, (existing, replacement) -> replacement, TreeMap::new));
        System.out.println("⑦" + map7);

        Map<String, Fruit> map8 = fruits.stream().collect(Collectors.toMap(Fruit::getAlias, v -> v, (existing, replacement) -> existing));
        System.out.println("⑧" + map8);

    }

    @Test
    public void toConcurrentMap(){
        List<String> fruits = List.of("apple","banana","apple","watermelon","orange");
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
        List<String> fruits = List.of("apple","banana","apple","watermelon","orange");
        Set<String> set = fruits.stream().collect(Collectors.toSet());
        System.out.println(set);
    }

    @Test//返回一个收集器，它将输入元素累积到一个集合中
    public void toCollection(){
        List<String> fruits = List.of("apple","banana","apple","watermelon","orange");

        Set<String> set = fruits.stream().collect(Collectors.toCollection(TreeSet::new));
        System.out.println(set);

        List<String> fruitss = set.stream().collect(Collectors.toCollection(LinkedList::new));
        System.out.println(fruitss);
    }

    @Test//它根据Predicate对输入元素进行分区，并将它们组织成Map <Boolean，List <T >>
    public void partitioningBy(){
        List<String> fruits = List.of("apple","banana","chry","watermelon","orange");

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
        List<String> fruits = List.of("apple","banana","apple","watermelon","orange");

        System.out.println(fruits.stream().map(String::toUpperCase).collect(Collectors.toList()));

        System.out.println(fruits.stream().map(String::toUpperCase).collect(Collectors.joining(",")));

        List<Fruit> fruitss = List.of(new Fruit("a", "apple"),new Fruit("b", "banana"),new Fruit("a", "abc"));

        Map<String, String> map = fruitss.stream().collect(Collectors.groupingBy(Fruit::getAlias, Collectors.mapping(Fruit::getName, Collectors.joining(","))));

        System.out.println(map);

        Map<String, List<String>> map3 = fruitss.stream().collect(Collectors.groupingBy(Fruit::getAlias, Collectors.mapping(Fruit::getName, Collectors.toList())));

        System.out.println(map3);
    }
}
