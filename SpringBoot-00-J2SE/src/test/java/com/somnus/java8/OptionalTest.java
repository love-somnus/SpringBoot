package com.somnus.java8;

import com.google.common.collect.Lists;
import com.somnus.java8.lambda.Fruit;
import org.junit.Test;

import java.util.*;

/**
 * @author Somnus
 * @packageName com.somnus.java8
 * @title: OptionalTest
 * @description: TODO
 * @date 2019/4/3 16:51
 */
public class OptionalTest {

    @Test//该方法通过一个非 null 的 value 来构造一个 Optional，返回的 Optional 包含了 value 这个值。
    // 对于该方法，传入的参数一定不能为 null，否则便会抛出 NullPointerException
    public void test1(){
        Optional<String> name = Optional.of("Sanaulla");
        System.out.println(name.get());

        Optional<String> name2 = Optional.of(null);
        System.out.println(name2.get());
    }

    @Test//该方法和 of 方法的区别在于，传入的参数可以为 null
    public void test2(){
        Optional<String> name = Optional.ofNullable("Sanaulla");
        System.out.println(name.get());

        Optional<String> name2 = Optional.ofNullable(null);
        System.out.println(name2.get());
    }

    @Test//如果 Optional 中有值，则对该值调用 consumer.accept，否则什么也不做
    public void test2_(){
        Optional<String> name = Optional.ofNullable("Sanaulla");
        name.ifPresent(o -> System.out.println(name.get()));

        Optional<String> name2 = Optional.ofNullable(null);
        name2.ifPresent(o -> System.out.println(name2.get()));
    }

    @Test//如果Optional实例有值则将其返回，否则返回orElse方法传入的参数
    public void test3(){
        Optional<String> name = Optional.ofNullable("Sanaulla");
        System.out.println(name.orElse("Sam"));

        Optional<String> name2 = Optional.ofNullable(null);
        System.out.println(name2.orElse("Sam"));
    }

    @Test//orElseGet与orElse方法类似，区别在于得到的默认值。
    // orElse方法将传入的字符串作为默认值，orElseGet方法可以接受Supplier接口的实现用来生成默认值。
    public void test5(){
        Optional<String> name = Optional.ofNullable("Sanaulla");
        System.out.println(name.orElse("####"));

        Optional<String> name2 = Optional.ofNullable(null);
        System.out.println(name2.orElseGet(()-> {
            System.out.println("&&&&&&&&&&&&");
            return null;
        }));
    }


    @Test//orElseThrow 与 orElse 方法的区别在于，orElseThrow 方法当 Optional 中有值的时候，返回值；
    // 没有值的时候会抛出异常
    public void test6(){
        Optional<String> name = Optional.ofNullable("Sanaulla");
        name.orElseThrow(() -> new RuntimeException());

        Optional<String> name2 = Optional.ofNullable(null);
        name2.orElseThrow(() -> new RuntimeException());
    }

    @Test
    public void test6_(){
        Optional<String> name = Optional.ofNullable("Sanaulla");
        name.orElseThrow(IllegalArgumentException::new);

        Optional<String> name2 = Optional.ofNullable(null);
        name2.orElseThrow(IllegalArgumentException::new);
    }

    @Test
    public void test7(){
        Optional<String> name = Optional.ofNullable(new Fruit("a",21))
                .map(stu -> stu.getName())
                .map(str -> str.toUpperCase());
        System.out.println(name.orElse("Sam"));

        String name2 = Optional.ofNullable(new Fruit()).map(stu -> stu.getName()).orElse("somnus");
        System.out.println(name2);

        System.out.println(Optional.ofNullable(15).filter(d -> d<10).map(d -> "0"+d).orElse("15"));
    }

    @Test
    public void test8(){
        Optional<String> name = Optional.ofNullable(new Fruit("a",21))
                .filter(stu -> stu.getNum() < 20)
                .map(stu -> stu.getName());
        System.out.println(name.orElse("Sam"));

        Optional.ofNullable(null).ifPresent(value -> {
            System.out.println("&&&&&&&&&&");
        });
        System.out.println("**************");
    }

    @Test
    public void test9(){
        Optionally.ofNullable(99).existThrow(value -> value<10, ()-> new IllegalStateException());
        Optionally.ofNullable(Lists.newArrayList()).existThrow(list -> list.isEmpty(), ()-> new NullPointerException());
    }

    @Test
    public void test10(){
        Optionally.ofNullable(99).ifTrue(value -> value > 10, value -> System.out.println(value));
        //不成立
        Integer integer = 99;
        Optionally.ofNullable(integer).ifTrue(value -> value < 10, value -> System.out.println(value));
        //不成立
        Integer integer2 = null;
        Optionally.ofNullable(integer2).ifTrue(value -> value > 10, value -> System.out.println(value));
    }

    @Test
    public void test11(){
        List list = null;
        Optionally.ofNullable(list).ifNotEmpty(value -> value.forEach(System.out::println));

        List list2 =  new ArrayList();
        Optionally.ofNullable(list2).ifNotEmpty(value -> value.forEach(System.out::println));

        List<Integer> list3 =  Lists.newArrayList(1, 2 ,3);
        Optionally.ofNullable(list3).ifNotEmpty(value -> value.forEach(System.out::println));
    }

    @Test
    public void test11_(){
        List list = null;
        Optionally.ofNullable(list).ifEmpty(value -> {
            System.out.println("####");
        });

        List list2 =  new ArrayList();
        Optionally.ofNullable(list2).ifEmpty(value -> {
            System.out.println("$$$$");
        });

        List<Integer> list3 =  Lists.newArrayList(1, 2 ,3);
        Optionally.ofNullable(list3).ifEmpty(value -> {
            System.out.println("@@@@");
        });
    }

    @Test
    public void test12(){
        String s = null;
        Optionally.ofNullable(s).ifNotEmpty(value -> System.out.println(s));

        String s2 = "";
        Optionally.ofNullable(s2).ifNotEmpty(value -> System.out.println(s2));

        String s3 = "12";
        Optionally.ofNullable(s3).ifNotEmpty(value -> System.out.println(s3));

        Integer i = 12;
        Optionally.ofNullable(i).ifNotEmpty(value -> System.out.println(i));
    }

    @Test
    public void test12_(){
        String s = null;
        Optionally.ofNullable(s).ifEmpty(value -> System.out.println("####"));

        String s2 = "";
        Optionally.ofNullable(s2).ifEmpty(value -> System.out.println("$$$$"));

        String s3 = "12";
        Optionally.ofNullable(s3).ifEmpty(value -> System.out.println("@@@@"));

        Integer i = 12;
        Optionally.ofNullable(s3).ifEmpty(value -> System.out.println("@@@@"));
    }

}
