package com.somnus.java8.lambda;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * @author Kevin
 * @packageName com.somnus.java8.lambda
 * @title: Java8Out
 * @description: TODO
 * @date 2020/7/7 10:22
 */
public class Java8Out {

    @Test
    public void testPeekAndforeach() {
        List<String> sentences = List.of("hello world","Jia Gou Wu Dao");
        // 演示点1： 仅peek操作，最终不会执行
        System.out.println("----before peek----");
        sentences.stream().peek(System.out::println);
        System.out.println("----after peek----");
        // 演示点2： 仅foreach操作，最终会执行
        System.out.println("----before foreach----");
        sentences.stream().forEach(System.out::println);
        System.out.println("----after foreach----");
        // 演示点3：peek 独自调用时并没有被执行、但 peek 后面加上终止操作之后便可以被执行，而 foreach 可以直接被执行
        System.out.println("----before peek and count----");
        sentences.stream().peek(System.out::println).findAny().get();
        System.out.println("----after peek and count----");
    }

    @Test
    public void forEach(){
        List<String> fruits = Lists.newArrayList("apple","banana","cherry","watermelon","orange");

        fruits.forEach(System.out::println);

        fruits.stream().forEach(System.out::println);
    }

    @Test
    public void forEachOrdered(){
        List<String> fruits = Lists.newArrayList("apple","banana","cherry","watermelon","orange");

        fruits.forEach(System.out::println);
        System.out.println("*******************************************");

        fruits.parallelStream().forEach(item -> System.out.println(Thread.currentThread().getName()+ ">>"+ item));
        System.out.println("*******************************************");

        //如果并行处理时，希望最后顺序是按照原来Stream的数据顺序，那可以调用forEachOrdered()
        fruits.parallelStream().forEachOrdered(item -> System.out.println(Thread.currentThread().getName()+ ">>"+ item));
        System.out.println("*******************************************");

        Set<String> fruitss = Sets.newHashSet("apple","banana","cherry","watermelon","orange");
        fruitss.stream().sorted().forEachOrdered(System.out::println);
        System.out.println("*******************************************");

        fruitss.stream().forEachOrdered(System.out::println);
    }
}
