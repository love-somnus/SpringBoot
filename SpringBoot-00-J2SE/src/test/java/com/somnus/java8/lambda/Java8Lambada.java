package com.somnus.java8.lambda;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.primitives.Ints;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Kevin
 * @packageName com.somnus.java8.lambda
 * @title: Java8Lambada
 * @description: TODO
 * @date 2020/7/7 11:28
 */
public class Java8Lambada {

    /**
     * 1.使用parallelStream可以简洁高效的写出并发代码
     * 2.parallelStream并行执行是无序的
     * 3.parallelStream提供了更简单的并发执行的实现，但并不意味着更高的性能，它是使用要根据具体的应用场景。
     *   如果cpu资源紧张parallelStream不会带来性能提升；如果存在频繁的线程切换反而会降低性能
     * 4.任务之间最好是状态无关的，因为parallelStream默认是非线程安全的，可能带来结果的不确定性
     */
    @Test
    public void parallel() {

        List<String> fruits = Lists.newArrayList("apple","banana","apple","watermelon","orange");

        fruits.stream().forEach(item -> System.out.println(Thread.currentThread().getName()+ ">>"+ item));

        System.out.println("-----------------------------------------------------------------");

        fruits.parallelStream().forEach(item -> System.out.println(Thread.currentThread().getName()+ ">>"+ item));

        System.out.println("-----------------------------------------------------------------");

        //日常工作中，经常会遇到遍历一个很大的集合做重复的操作，这时候如果使用串行执行会相当耗时，因此一般会采用多线程来提速。
        //Java8的paralleStream用fork/join框架提供了并发执行能力。但是如果使用不当，很容易陷入误区。
        //tream.parallel.forEach()中执行的操作并非线程安全。如果需要线程安全，
        // 可以把集合转换为同步集合，即：Collections.synchronizedList(new ArrayList<>()) 或者使用JUC中的CopyOnWriteArrayList类进行替换

        System.out.println("串行执行的大小：" + IntStream.range(0, 100000).boxed().map(value -> value-1).collect(Collectors.toCollection(ArrayList::new)).size());
        System.out.println("并行执行的大小：" + IntStream.range(0, 100000).boxed().parallel().map(value -> value-1).collect(Collectors.toCollection(ArrayList::new)).size());
        System.out.println("线程安全并行执行的大小：" + IntStream.range(0, 100000).boxed().map(value -> value-1).parallel().collect(Collectors.toCollection(CopyOnWriteArrayList::new)).size());

        System.out.println("并行执行的大小(IntStream独有的)：" + IntStream.range(0, 10000).boxed().map(value -> value-1).parallel().collect(ArrayList::new, List::add, List::addAll).size());
        /**
         * 上面我们也看到了parallelStream所带来的隐患和好处,那么,在从stream和parallelStream方法中进行选择时,我们可以考虑以下几个问题：
         * 1. 是否需要并行？
         * 2. 任务之间是否是独立的？是否会引起任何竞态条件？
         * 3. 结果是否取决于任务的调用顺序？
         *
         * 对于问题1，在回答这个问题之前，你需要弄清楚你要解决的问题是什么，数据量有多大，计算的特点是什么？
         * 并不是所有的问题都适合使用并发程序来求解，比如当数据量不大时，顺序执行往往比并行执行更快。
         * 毕竟，准备线程池和其它相关资源也是需要时间的。但是，当任务涉及到I/O操作并且任务之间不互相依赖时，
         * 那么并行化就是一个不错的选择。通常而言，将这类程序并行化之后，执行速度会提升好几个等级。
         *
         * 对于问题2，如果任务之间是独立的，并且代码中不涉及到对同一个对象的某个状态或者某个变量的更新操作，那么就表明代码是可以被并行化的。
         *
         * 对于问题3，由于在并行环境中任务的执行顺序是不确定的，因此对于依赖于顺序的任务而言，并行化也许不能给出正确的结果。
         */
    }

    @Test
    public void map(){

        List<String> fruits = Lists.newArrayList("apple","banana","apple","watermelon","orange");

        fruits.stream().map(String :: toUpperCase).forEach(System.out::println);

        fruits.stream().map(String :: length).forEach(System.out::println);

        List<String> numbers = Lists.newArrayList("1","3","5","7","9");
        List<Integer> nums = numbers.stream().map(Integer :: valueOf).collect(Collectors.toList());
        System.out.println(nums);

        Multimap<String, Integer> params = ImmutableMultimap.of("a", 1, "b", 2, "c", 3, "a", 2);
        String str = params.entries().stream().map(p -> p.getKey() + "=" + p.getValue()).reduce((p1, p2) -> p1 + "&" + p2).map(s -> "?" + s).orElse("");
        System.out.println(str);

        Map<String, Integer> params2 = ImmutableMap.of("a", 1, "b", 2, "c", 3);
        String str2 = params2.entrySet().stream().map(p -> p.getKey() + "=" + p.getValue()).reduce((p1, p2) -> p1 + "&" + p2).map(s -> "?" + s).orElse("");
        System.out.println(str2);
    }

    @Test
    public void limit(){
        List<String> fruits = Lists.newArrayList("apple","banana","apple","watermelon","orange");
        fruits.stream().limit(2).forEach(System.out::println);
    }

    @Test
    public void skip(){
        List<String> fruits = Lists.newArrayList("apple","banana","apple","watermelon","orange");
        fruits.stream().skip(2).forEach(System.out::println);
    }

    @Test
    public void distinct(){
        List<String> fruits = Lists.newArrayList("apple","banana","apple","watermelon","orange");
        fruits.stream().distinct().forEach(System.out::println);

        List<Fruit> fruitss = Lists.newArrayList(new Fruit("a", "apple"),new Fruit("b", "banana"),new Fruit("a", "apple"),new Fruit("a", "almond"));
        fruitss.stream().distinct().forEach(System.out::println);

        ArrayList<Fruit> collect1 = fruitss.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(
                        Comparator.comparing(p -> p.getAlias() + ";" + p.getName()))), ArrayList::new));
        System.out.println(collect1);
    }

    @Test
    public void sorted(){
        List<Integer> nums = Ints.asList(3, 3, 2, 6, 3, 4, 3, 7, 8, 9, 2);
        System.out.println(nums.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList()));
        System.out.println(nums.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
        System.out.println(nums.stream().sorted(Comparator.comparingInt(num -> num)).collect(Collectors.toList()));

        List<Fruit> fruits = Lists.newArrayList(new Fruit("apple", 5),new Fruit("banana", 5),new Fruit("apricot", 7));
        System.out.println(fruits.stream().sorted(Comparator.comparing(Fruit::getName).reversed()).collect(Collectors.toList()));
        System.out.println(fruits.stream().sorted(Comparator.comparingInt(Fruit::getNum).reversed()).collect(Collectors.toList()));
        System.out.println(fruits.stream().sorted(Comparator.comparingInt(Fruit::getNum).thenComparing(Fruit::getName)).collect(Collectors.toList()));

        List<String> fruitss = Lists.newArrayList("apple","banana","apple","watermelon","orange");
        System.out.println(fruitss.stream().sorted(String::compareToIgnoreCase).collect(Collectors.toList()));
    }

    @Test
    public void find(){
        List<String> fruits = Lists.newArrayList("apple","banana","cherry","watermelon","orange");
        //findAny返回的是最快处理完的那个线程的数据
        System.out.println(fruits.parallelStream().findAny().get());
        System.out.println(fruits.stream().findFirst().get());
    }

    @Test
    public void filter(){
        List<String> fruits = Lists.newArrayList("apple","banana","cherry","watermelon","orange", "app");
        System.out.println(fruits.stream().filter(fruit -> fruit.startsWith("a")).collect(Collectors.toList()));

        List<Fruit> fruitss = Lists.newArrayList(new Fruit(), new Fruit("b", "banana"));
        System.out.println(fruitss.stream().map(Fruit::getName).filter(Objects::nonNull).collect(Collectors.toList()));

        System.out.println(fruits.stream().filter(s -> s.startsWith("a")).mapToInt(String::length).max().orElse(0));;
    }

    @Test
    public void reduce(){
        List<Integer> nums = Ints.asList(1, 2, 3);
        //有起始值
        System.out.println("有起始值总和: " + nums.stream().reduce(0, (a,b) -> a + b));
        System.out.println("有起始值总和: " + nums.stream().reduce(0, Integer::sum));
        System.out.println("有起始值最大值: " + nums.stream().reduce(0, Integer::max));
        System.out.println("有起始值最小值: " + nums.stream().reduce(0, Integer::min));

        //无起始值(返回Optional类型的数据，该类型是java8中新增的，主要用来避免空指针异常)
        System.out.println("无起始值总和: " + nums.stream().reduce((a,b) -> a + b).get());
        System.out.println("无起始值总和: " + nums.stream().reduce(Integer::sum).get());
        System.out.println("无起始值最大值: " + nums.stream().reduce(Integer::max).get());
        System.out.println("无起始值最小值: " + nums.stream().reduce(Integer::min).get());

        List<BigDecimal> nums2 = Lists.newArrayList(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
        System.out.println("BigDecimal有起始值总和: " + nums2.stream().reduce(BigDecimal.ZERO, (a,b) -> a.add(b)));
        System.out.println("BigDecimal有起始值总和: " + nums2.stream().reduce(BigDecimal.ZERO, BigDecimal::add));

        System.out.println("BigDecimal无起始值总和: " + nums2.stream().reduce((a,b) -> a.add(b)).get());
        System.out.println("BigDecimal无起始值总和: " + nums2.stream().reduce(BigDecimal::add).get());

        //字符串处理
        List<String> fruits = Lists.newArrayList("apple","banana","cherry","watermelon","orange");
        System.out.println("最长的单词-->" + fruits.stream().reduce((s1, s2) -> s1.length()>=s2.length() ? s1 : s2).get());
        System.out.println("单词的长度之和-->" + fruits.stream().reduce(0, (sum, str) -> sum + str.length(), (a, b) -> a+b));
        System.out.println("字符串连接-->" + fruits.stream().reduce("", String::concat));
        System.out.println("最先一个水果-->" + fruits.stream().reduce((first, second) -> first).get());
        System.out.println("最后一个水果-->" + fruits.stream().reduce((first, second) -> second).get());
    }

    @Test
    public void match(){
        System.out.println(Boolean.valueOf("true"));
        List<Integer> nums = Ints.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println("全部大于2，才会返回true --> " + nums.stream().allMatch(num -> num > 2));
        System.out.println("任意一个小于2，就返回true --> " + nums.stream().anyMatch(num -> num > 2));
        System.out.println("没有一个大于2，才会返回true --> " + nums.stream().noneMatch(num -> num > 2));

        System.out.println("任意一个大于20，就返回true --> " + nums.stream().map(num -> num > 20).reduce(false, (a, b) -> a || b));
    }

    @Test
    public void max(){
        List<Integer> nums = Ints.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println("最大值: " + nums.stream().max(Comparator.comparing(num -> num)).get());
        System.out.println("最小值: " + nums.stream().min(Comparator.naturalOrder()).get());

        List<Fruit> fruits = Lists.newArrayList(new Fruit("apple", 5),new Fruit("banana", 6),new Fruit("apricot", 7));
        System.out.println(fruits.stream().max(Comparator.comparing(Fruit::getName)).get());
        System.out.println(fruits.stream().min(Comparator.comparingInt(Fruit::getNum)).get());
    }

    @Test
    public void flatMap(){
        List<String> fruits = Lists.newArrayList("apple","banana","cherry","watermelon","orange");
        System.out.println(fruits.stream().map(fruit -> fruit.split("")).flatMap(Arrays::stream).collect(Collectors.toList()));
        System.out.println(fruits.stream().flatMap(fruit -> Stream.of(fruit.split(""))).collect(Collectors.toList()));


        List<Map<String,Integer>> list = Lists.newArrayList(ImmutableMap.of("a", 1, "b", 2), ImmutableMap.of("c", 1, "d", 2), ImmutableMap.of("a", 3, "d", 4));
        // 覆盖key相同的值，
        Map<String,Object> map = list.stream().map(Map::entrySet).flatMap(Set::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existing, replacement) -> replacement));
        System.out.println(map);

        // 覆盖key相同的值，
        Map<String,Object> map2 = list.stream().flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existing, replacement) -> replacement));
        System.out.println(map2);

        // 覆盖key相同的值，
        Map<String,Object> map3 = list.stream().map(Map::entrySet).flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existing, replacement) -> replacement));
        System.out.println(map3);

        List<List<Integer>> numss = Lists.newArrayList(Ints.asList(3, 2), Ints.asList(2, 5));
        System.out.println(numss.stream().filter(Objects::nonNull).flatMap(Collection::stream)/*.distinct()*/
                .collect(Collectors.toList()));
        System.out.println(Arrays.toString(
                numss.stream().filter(Objects::nonNull).flatMap(Collection::stream)/*.distinct()*/
                        .toArray(Integer[]::new)
        ));

        List<String[]> arrayList = Lists.newArrayList(new String[]{"10111011001", "10119910001"},  new String[]{"11111017501", "10119910001"});
        System.out.println(arrayList.stream().filter(Objects::nonNull).flatMap(Arrays::stream)/*.distinct()*/
                .collect(Collectors.toList()));
        System.out.println(Arrays.toString(
                arrayList.stream().filter(Objects::nonNull).flatMap(Arrays::stream)/*.distinct()*/
                        .toArray(String[]::new)
        ));

    }

}
