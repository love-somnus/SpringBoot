package com.somnus.java8;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import com.google.common.collect.*;
import com.google.common.primitives.Ints;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2019/1/10 14:33
 */
public class JDKTest {
    @Test
    public void foreach() {

        List<String> list = Lists.newArrayList("a","b","c","d","e","c","f");

        list.forEach(item -> System.out.print(item));

        list.forEach(item -> {System.out.print(item);});

        list.forEach(System.out::print);

        list.stream().map(item -> item.toUpperCase()).forEach(item -> System.out.print(item));

        list.stream().map(String :: toUpperCase).forEach(item -> System.out.print(item));

        System.out.println("-----------------------------------------------------------------");

        //从前截断
        list.stream().limit(2).map(String :: toUpperCase).forEach(item -> System.out.print(item));

        System.out.println("-----------------------------------------------------------------");

        //跳过前边
        list.stream().skip(2).map(String :: toUpperCase).forEach(item -> System.out.print(item));

        System.out.println("-----------------------------------------------------------------");

        //去重
        list.stream().distinct().map(String :: toUpperCase).forEach(item -> System.out.print(item));

    }

    /**
     * 1.使用parallelStream可以简洁高效的写出并发代码
     * 2.parallelStream并行执行是无序的
     * 3.parallelStream提供了更简单的并发执行的实现，但并不意味着更高的性能，它是使用要根据具体的应用场景。
     *   如果cpu资源紧张parallelStream不会带来性能提升；如果存在频繁的线程切换反而会降低性能
     * 4.任务之间最好是状态无关的，因为parallelStream默认是非线程安全的，可能带来结果的不确定性
     */
    @Test
    public void parallel() {

        List<String> list = Lists.newArrayList("a","b","c","d","e");

        list.stream().forEach(item -> System.out.println(Thread.currentThread().getName()+ ">>"+ item));

        System.out.println("-----------------------------------------------------------------");

        list.parallelStream().forEach(item -> System.out.println(Thread.currentThread().getName()+ ">>"+ item));

        System.out.println("-----------------------------------------------------------------");

        //如果并行处理时，希望最后顺序是按照原来Stream的数据顺序，那可以调用forEachOrdered()
        list.parallelStream().forEachOrdered(item -> System.out.println(Thread.currentThread().getName()+ ">>"+ item));

        System.out.println("-----------------------------------------------------------------");

        //日常工作中，经常会遇到遍历一个很大的集合做重复的操作，这时候如果使用串行执行会相当耗时，因此一般会采用多线程来提速。
        //Java8的paralleStream用fork/join框架提供了并发执行能力。但是如果使用不当，很容易陷入误区。
        //tream.parallel.forEach()中执行的操作并非线程安全。如果需要线程安全，
        // 可以把集合转换为同步集合，即：Collections.synchronizedList(new ArrayList<>()) 或者使用JUC中的CopyOnWriteArrayList类进行替换
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Integer> list3 = Collections.synchronizedList(Lists.newArrayList());

        IntStream.range(0, 10000).forEach(list1::add);
        IntStream.range(0, 10000).parallel().forEach(list2::add);
        IntStream.range(0, 10000).parallel().forEach(list3::add);

        System.out.println("串行执行的大小：" + list1.size());
        System.out.println("并行执行的大小：" + list2.size());
        System.out.println("线程安全并行执行的大小：" + list3.size());

        System.out.println("-----------------------------------------------------------------");

        IntStream.range(0, 10).parallel().forEach(item -> System.out.println(Thread.currentThread().getName()+ ">>"+ item));
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
    public void sort(){
        List<String> list2 = Lists.newArrayList("c","o","n","g");
        Collections.sort(list2, Comparator.naturalOrder());
        System.out.println(list2);

        List<String> list = Lists.newArrayList("c","o","n","g");
        Collections.sort(list, Comparator.reverseOrder());
        System.out.println(list);

        List<Student> slist = Lists.newArrayList(new Student("apple",21),new Student("eamil",22),new Student("xyz",18));
        Collections.sort(slist, Comparator.comparing(Student::getAge));
        System.out.println(slist);
        List<Integer> nums = Ints.asList(10, 2, 18, 4, 5, 6, 12, 8, 15, 1, 11, 7, 13, 14, 9, 16, 17, 3, 19, 20);
        Collections.sort(nums, Comparator.comparing(num -> num));
        System.out.println(nums);

        List<Student> slist2 = Lists.newArrayList(new Student("apple",21),new Student("eamil",22),new Student("xyz",18));
        slist2.sort(Comparator.comparingInt(Student::getAge));
        System.out.println(slist2);

        List<Student> slist3 = Lists.newArrayList(new Student("apple",21),new Student("eamil",22),new Student("xyz",18));
        slist3.sort(Comparator.comparingInt(Student::getAge).reversed());
        System.out.println(slist3);

        List<Student> slist4 = Lists.newArrayList(new Student("apple",21),new Student("eamil",22),new Student("xyz",18));
        slist4.sort(Comparator.comparingInt(Student::getAge).thenComparing(Student::getName));
        System.out.println(slist4);

        List<String> fruits = Lists.newArrayList("apple","banana","cherry","watermelon","orange");
        System.out.println(fruits.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList()));
        System.out.println(fruits.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
    }

    @Test
    public void index(){
        List<String> list = Lists.newArrayList("c","o","n","g");

        Stream.iterate(0, i -> i + 1).limit(list.size()).forEach(i -> System.out.println(i + "---->" + list.get(i)));
    }

    @Test
    public void stream(){
        List<String> list = Lists.newArrayList("a","b","c","d","e");
        list.stream().forEach(item -> System.out.print(item));
        System.out.println("-----------------------------------------------------------------");

        Stream.of("a","b","c","d","e").forEach(item -> System.out.print(item));
        System.out.println("-----------------------------------------------------------------");

        String[] array = {"a","b","c","d","e"};
        Arrays.stream(array).forEach(item -> System.out.print(item));
        System.out.println("-----------------------------------------------------------------");

        String[] words = new String[]{"hello","world"};
        Arrays.stream(words).map(word -> word.split("")).flatMap(Arrays::stream).distinct().collect(Collectors.toList()).forEach(System.out::print);
        Arrays.stream(words).flatMap(word -> Stream.of(word.split(""))).distinct().collect(Collectors.toList()).forEach(System.out::print);
        System.out.println("-----------------------------------------------------------------");

        IntStream.range(0, 10).forEach(item -> System.out.println(Thread.currentThread().getName()+ ">>"+ item));
        System.out.println("-----------------------------------------------------------------");
        IntStream.rangeClosed(0, 10).forEach(item -> System.out.println(Thread.currentThread().getName()+ ">>"+ item));

        //随机生成
        System.out.println("-----------------------------------------------------------------");
        LongStream.generate(() -> System.nanoTime() % 10000).limit(10).forEach(System.out::println);

        //生成一个等差数列
        Stream.iterate(0, n -> n + 3).limit(10). forEach(x -> System.out.print(x + " "));

        Pattern.compile("\\W").splitAsStream("A B C"). forEach(letter -> System.out.print(letter + " "));
    }

    public int compute(int a, Function<Integer,Integer> function){
        int result = function.apply(a);
        return result;
    }

    public String convert(int a, Function<Integer,String> function){
        String result = function.apply(a);
        return result;
    }

    @Test
    public void func(){
        JDKTest test = new JDKTest();
        System.out.println(test.compute(2, value -> 5+value));

        System.out.println(test.convert(2, value -> value + "$"));
    }

    @Test
    public void toList(){
        List<Student> students = ImmutableList.of(new Student("a",21),new Student("ac",22),new Student("a",18));
        System.out.println(students.stream().map(student -> People.builder().name(student.getName()).age(student.getAge()).build()).collect(Collectors.toList()));
        System.out.println(students.stream().map(Student::getName).collect(Collectors.toList()));
        System.out.println(students.stream().map(student -> student.getName()).map(String::length).collect(Collectors.toList()));
        System.out.println(students.stream().map(Student::getAge).map(i -> i - 10).collect(Collectors.toList()));
    }

    @Test
    public void extremum(){
        List<Integer> nums = Ints.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        System.out.println(nums.stream().collect(Collectors.averagingDouble(num -> num)));
        System.out.println(nums.stream().collect(Collectors.summingInt(num -> num)));

        List<Student> students = ImmutableList.of(new Student("a",21),new Student("ac",22),new Student("a",18));
        System.out.println(students.stream().collect(Collectors.averagingDouble(Student::getAge)));
        System.out.println(students.stream().collect(Collectors.summingInt(Student::getAge)));

        //找出年龄最大、年龄最小的学生(对象)
        System.out.println(students.stream().max(Comparator.comparing(Student::getAge)).orElseGet(Student :: new));
        System.out.println(students.stream().max(Comparator.comparing(stu -> stu.getAge())).orElseGet(Student :: new));
        System.out.println(students.stream().min(Comparator.comparing(stu -> stu.getAge())).orElse(new Student()));
    }

    @Test
    public void joining(){
        List<String> fruits = Lists.newArrayList("apple","banana","cherry","watermelon","orange");
        System.out.println(fruits.stream().map(String::toUpperCase).collect(Collectors.joining()));
        System.out.println(fruits.stream().map(String::toUpperCase).collect(Collectors.joining(",")));
        System.out.println(fruits.stream().map(String::toUpperCase).collect(Collectors.joining(",", "(", ")")));
    }

    @Test
    public void reduce(){
        List<Integer> nums = Ints.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        //有起始值
        System.out.println(nums.stream().reduce(0, (a,b) -> a + b));
        System.out.println(nums.stream().reduce(0, Integer::sum));
        System.out.println(nums.stream().reduce(0, Integer::max));
        System.out.println(nums.stream().reduce(0, Integer::min));
        //无起始值(返回Optional类型的数据，改类型是java8中新增的，主要用来避免空指针异常)
        System.out.println(nums.stream().reduce((a,b) -> a + b).get());
        System.out.println(nums.stream().reduce(Integer::sum).get());
        System.out.println(nums.stream().reduce(Integer::max).get());
        System.out.println(nums.stream().reduce(Integer::min).get());

        List<String> fruits = Lists.newArrayList("apple","banana","cherry","watermelon","orange");
        System.out.println("最长的单词-->" + fruits.stream().reduce((s1, s2) -> s1.length()>=s2.length() ? s1 : s2).get());
        System.out.println("单词的长度之和-->" + fruits.stream().reduce(0, (sum, str) -> sum + str.length(), (a, b) -> a+b));
        System.out.println("字符串连接-->" + fruits.stream().reduce("", String::concat));
    }

    @Test
    public void match(){
        List<Integer> nums = Ints.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        System.out.println("全部大于2，才会返回true --> " + nums.stream().allMatch(num -> num > 2));
        System.out.println("任意一个小于20，就返回true --> " + nums.stream().anyMatch(num -> num > 2));
        System.out.println("没有一个大于2，才会返回true --> " + nums.stream().noneMatch(num -> num > 2));

        System.out.println("任意一个大于20，就返回true --> " + nums.stream().map(num -> num > 20).reduce(false, (a, b) -> a || b));
    }

    @Test
    public void find(){
        List<Integer> nums = Ints.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        //findAny返回的是最快处理完的那个线程的数据
        System.out.println(nums.parallelStream().findAny().orElse(0));
        System.out.println(nums.parallelStream().findFirst().orElse(0));
    }

    @Test
    public void filter(){
        List<Integer> nums = Ints.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        System.out.println(nums.stream().filter(num -> num> 10).collect(Collectors.toList()));
        System.out.println(nums.stream().count());
    }

    @Test
    public void statistics(){
        List<Integer> nums = Ints.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        //统计函数
        IntSummaryStatistics stats= nums.stream().mapToInt(x -> x).summaryStatistics();
        System.out.println("最大值: " + stats.getMax());
        System.out.println("最小值: " + stats.getMin());
        System.out.println("平均值: " + stats.getAverage());
        System.out.println("总数: " + stats.getCount());
        System.out.println("总和: " + stats.getSum());
    }

    @Test
    public void group() {
        List<Student> slist = ImmutableList.of(new Student("a", 21), new Student("ac", 22), new Student("a", 18));
        //计数
        Map<String, Long> map = slist.stream().collect(Collectors.groupingBy(Student::getName, Collectors.counting()));
        System.out.println(map);
        //排序
        map.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).forEachOrdered(System.out::println);
        //累加求和
        Map<String, Integer> sum = slist.stream().collect(Collectors.groupingBy(Student::getName, Collectors.summingInt(Student::getAge)));
        System.out.println(sum);
        //分组
        Map<String, List<Student>> groupMap = slist.stream().collect(Collectors.groupingBy(Student::getName));
        System.out.println(groupMap);

        //上述代码根据name将list分组，如果name是唯一的，那么上述代码就会显得啰嗦。我们需要知道，Guava补JDK之不足，现在改Guava一显身手了
        List<Student> ulist = ImmutableList.of(new Student("a", 21), new Student("ac", 22), new Student("d", 18));
        Map<String, Student> guava = Maps.uniqueIndex(ulist, Student::getName);
        System.out.println(guava);
    }

    @Test
    public void map(){
        Multimap<String, Integer> params = ImmutableMultimap.of("a", 1, "b", 2, "c", 3, "a", 11);
        String str = params.entries().stream().map(p -> p.getKey() + "=" + p.getValue()).reduce((p1, p2) -> p1 + "&" + p2).map(s -> "?" + s).orElse("");
        System.out.println(str);
    }

    @Test
    public void map2(){
        Map<String, Integer> params = ImmutableMap.of("a", 1, "b", 2, "c", 3);
        String str = params.entrySet().stream().map(p -> p.getKey() + "=" + p.getValue()).reduce((p1, p2) -> p1 + "&" + p2).map(s -> "?" + s).orElse("");
        System.out.println(str);
    }

    @Test
    public void map2list(){
        Map<String, String> params = ImmutableMap.of("a", "apple", "b", "banana", "c", "cat");
        List<String> list = params.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey())).map(e -> e.getValue()).collect(Collectors.toList());
        System.out.println(list);
        List<String> list2 = params.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).map(e -> e.getValue()).collect(Collectors.toList());
        System.out.println(list2);
        List<String> list3 = params.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(e -> e.getValue()).collect(Collectors.toList());
        System.out.println(list3);
    }

    @Test
    public void list2map(){
        List<Student> list = ImmutableList.of(new Student("a",21),new Student("ac",22),new Student("df",18));

        Map<String, Integer> map = list.stream().collect(Collectors.toMap(Student::getName, Student::getAge));
        System.out.println(map);

        Map<String, Student> map2 = list.stream().collect(Collectors.toMap(Student::getName, Function.identity()));
        System.out.println(map2);

        List<String> lists = Lists.newArrayList("good", "astonishment", "seize", "preferable");
        Map<String, Integer> map3 = lists.stream().collect(Collectors.toMap(Function.identity(), String::length));
        System.out.println(map3);
    }

    @Test
    public void list2array(){
        List<Integer> lists = Ints.asList(1, 3, 5, 7);
        int[] strings = lists.stream().mapToInt(str -> str).toArray();
        System.out.println(strings);
    }

    @Test
    public void nulll(){
        Student stu = new Student(null,21);
        System.out.println(stu.getName());
        System.out.println(Objects.isNull(stu.getName()));
    }
}
@Data
@NoArgsConstructor
@AllArgsConstructor
class Student{
    private String name;
    private int age;
}
@Builder
class People{
    private String name;
    private int age;
}

