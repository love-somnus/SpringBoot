package com.somnus.java8;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.Consumer;

/**
 * @author Kevin
 * @packageName com.somnus.java8
 * @title: Consumer1accpect
 * @description: Consumer<T>  接收参数，无返回结果
 * @date 2020/5/14 18:38
 */
@RequiredArgsConstructor
public class Java8Consumer {

    private final Set<String> sets = new HashSet<>();

    private final List<String> lists = new ArrayList<>();

    private final Map<String, String> maps = new HashMap<>();

    public static void main(String[] args) {
        Consumer<Integer> times2 = value -> System.out.println(value * 2);
        times2.accept(10);

        Consumer<Integer> squared = value -> System.out.println(value * value);
        squared.accept(10);

        //先执行调用者times2，再执行参数squared
        times2.andThen(squared).accept(10);

        Java8Consumer.accept(10 , System.out::println);

        Java8Consumer.accept(10 ,value -> System.out.println(value * value));

        Java8Consumer consumer = new Java8Consumer();
        consumer
                .acceptSet(v -> {
                    v.addAll(Collections.unmodifiableSet(new HashSet<>(Arrays.asList("1", "2", "3"))));
                    v.add("4");
                })
                .acceptList(v -> {
                    v.addAll(Collections.unmodifiableList(Arrays.asList("1", "2", "3")));
                    v.add("4");
                })
                .acceptMap(v -> {
                    v.putAll(Collections.unmodifiableMap(ImmutableMap.of("a", "1")));
                    v.put("b", "2");
                });
        System.out.println(consumer);
    }

    static void accept(Integer num, Consumer<Integer> consumer){
        consumer.accept(num);
    }

    Java8Consumer acceptSet(Consumer<Set<String>> consumer){
        consumer.accept(this.sets);
        return this;
    }

    Java8Consumer acceptList(Consumer<List<String>> consumer){
        consumer.accept(this.lists);
        return this;
    }

    Java8Consumer acceptMap(Consumer<Map<String, String>> consumer){
        consumer.accept(this.maps);
        return this;
    }
}
