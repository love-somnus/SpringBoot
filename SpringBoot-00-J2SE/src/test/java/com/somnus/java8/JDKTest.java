package com.somnus.java8;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2019/1/10 14:33
 */
public class JDKTest {
    @Test
    public void foreach() {

        List<String> list = Arrays.asList("a","b","c","d","b");

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

    @Test
    public void sort(){
        List<String> list2 = Arrays.asList("c","o","n","g");
        Collections.sort(list2, Comparator.naturalOrder());
        System.out.println(list2);

        List<String> list = Arrays.asList("c","o","n","g");
        Collections.sort(list, Comparator.reverseOrder());
        System.out.println(list);

        List<Student> slist = Lists.newArrayList(new Student("apple",21),new Student("eamil",22),new Student("xyz",18));
        Collections.sort(slist, Comparator.comparing(Student::getAge));
        System.out.println(slist);

        List<Student> slist2 = Lists.newArrayList(new Student("apple",21),new Student("eamil",22),new Student("xyz",18));
        slist2.sort(Comparator.comparingInt(Student::getAge));
        System.out.println(slist2);

        List<Student> slist3 = Lists.newArrayList(new Student("apple",21),new Student("eamil",22),new Student("xyz",18));
        slist3.sort(Comparator.comparingInt(Student::getAge).reversed());
        System.out.println(slist3);

        List<Student> slist4 = Lists.newArrayList(new Student("apple",21),new Student("eamil",22),new Student("xyz",18));
        slist4.sort(Comparator.comparingInt(Student::getAge).thenComparing(Student::getName));
        System.out.println(slist4);
    }

    @Test
    public void index(){
        List<String> list = Arrays.asList("c","o","n","g");

        Stream.iterate(0, i -> i + 1).limit(list.size()).forEach(i -> System.out.println(i + "---->" + list.get(i)));
    }

    @Test
    public void steam(){
        List<String> list = Arrays.asList("c","o","n","g");
        list.stream().forEach(item -> System.out.println(item));

        Stream.of("c","o","n","g").forEach(item -> System.out.println(item));

        String[] array = {"c","o","n","g"};
        Arrays.stream(array).forEach(item -> System.out.println(item));
    }

    public int compute(int a,Function<Integer,Integer> function){
        int result = function.apply(a);
        return result;
    }

    public String convert(int a,Function<Integer,String> function){
        String result = function.apply(a);
        return result;
    }

    @Test
    public void func(){
        JDKTest test = new JDKTest();
        System.out.println(test.compute(2, value -> {return 2*value;}));
        System.out.println(test.compute(2, value -> 5+value));

        System.out.println(test.convert(2, value -> value + "$"));
    }

    @Test
    public void list(){
        List<Student> slist = ImmutableList.of(new Student("a",21),new Student("ac",22),new Student("a",18));

        List<People> plist = slist.stream().map(student -> {
            return People.builder().name(student.getName()).age(student.getAge()).build();
        }).collect(Collectors.toList());
        plist.forEach(System.out::println);

        List<People> plist2 = slist.stream().map(student -> People.builder().name(student.getName()).age(student.getAge()).build())
                .collect(Collectors.toList());
        plist2.forEach(System.out::println);

        //使用map方法获取list数据中的name
        List<String> names = slist.stream().map(Student::getName).collect(Collectors.toList());
        System.out.println(names);

        //使用map方法获取list数据中的name的长度
        List<Integer> length = slist.stream().map(Student::getName).map(String::length).collect(Collectors.toList());
        System.out.println(length);

        //将每人的年龄-10
        List<Integer> score = slist.stream().map(Student::getAge).map(i -> i - 10).collect(Collectors.toList());
        System.out.println(score);

        //计算学生总年龄
        Integer totalScore = slist.stream().mapToInt(Student::getAge).sum();
        System.out.println("总年龄" + totalScore);
        Integer totalScore1 = slist.stream().map(Student::getAge).reduce(0,(a,b) -> a + b);
        System.out.println("总年龄" + totalScore1);

        //计算学生年龄，返回Optional类型的数据，改类型是java8中新增的，主要用来避免空指针异常
        Optional<Integer> totalScore2 = slist.stream().map(Student::getAge).reduce((a,b) -> a + b);
        System.out.println("总年龄" + totalScore2.orElse(0));

        //计算最高年龄和最低年龄
        Optional<Integer> max = slist.stream().map(Student::getAge).reduce(Integer::max);
        Optional<Integer> min = slist.stream().map(Student::getAge).reduce(Integer::min);

        System.out.println(max.orElse(0));
        System.out.println(min.orElse(0));

        //查询平均值,求和
        double avg = slist.stream().collect(Collectors.averagingDouble(Student::getAge));
        double sum = slist.stream().collect(Collectors.summingDouble(Student::getAge));
        System.out.println(avg);
        System.out.println(sum);

        //全部大于2岁，才会返回true
        boolean all = slist.stream().allMatch(student -> student.getAge() > 2);
        //任意一个人小于20岁，就返回true
        boolean any = slist.stream().anyMatch(student -> student.getAge() < 20);
        //没有一个人大于2岁，才会返回true
        boolean none = slist.stream().noneMatch(student -> student.getAge() > 2);
        System.out.println(all + "|" + any + "|" +  none);
        boolean any2 = slist.stream().map(student -> student.getAge() < 10).reduce(false, (a, b) -> a || b);
        System.out.println(any2);
    }

    @Test
    public void list2(){
        List<String> slist = ImmutableList.of("red", "green","black","white","grey");
        List<String> collect = slist.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println(collect);
        //连接
        String join = slist.stream().map(String::toUpperCase).collect(Collectors.joining(","));
        System.out.println(join);

        List<String> collect2 = slist.stream().map(str -> {return str.concat("$");}).collect(Collectors.toList());
        System.out.println(collect2);

        List<Integer> num = Arrays.asList(1,2,3,4,5);
        List<Integer> collect3 = num.stream().map(n -> n * 2).collect(Collectors.toList());
        System.out.println(collect3); //[2, 4, 6, 8, 10]

        List<Integer> collect4 = num.stream().filter(n -> n != 2).collect(Collectors.toList());
        System.out.println(collect4); //[1, 3, 4, 5]

        Integer any = num.stream().filter(n -> n> 25).findAny().orElse(0);
        System.out.println(any);

        Integer first = num.stream().filter(n -> n> 25).findFirst().orElse(0);
        System.out.println(first);

        long count = num.stream().count();
        System.out.println(count);
    }
    @Test
    public void map(){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String str = params.entrySet().stream().map(p -> p.getKey() + "=" + p.getValue()).reduce((p1, p2) -> p1 + "&" + p2).map(s -> "?" + s).orElse("");
        System.out.println(str);
    }
    @Test
    public void map2(){
        Map<String, String> params = new HashMap<>();
        params.put("key", "1");
        params.put("storeId", "a");
        params.put("orderId", "b");
        params.put("orderId", "c");
        String str = params.entrySet().stream().map(p -> p.getKey() + "=" + p.getValue()).reduce((p1, p2) -> p1 + "&" + p2).map(s -> "?" + s).orElse("");
        System.out.println(str);
    }
    @Test
    public void map2list(){
        Map<String, String> params = new HashMap<>();
        params.put("key", "1");
        params.put("abc", "a");
        params.put("good", "b");
        params.put("fuck", "c");
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

        List<String> lists = Arrays.asList("good", "astonishment", "seize", "preferable");
        Map<String, Integer> map3 = lists.stream().collect(Collectors.toMap(Function.identity(), String::length));
        System.out.println(map3);
    }

    @Test
    public void array2list(){
        List<Integer> lists = Arrays.asList(1, 3, 5, 7);
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

