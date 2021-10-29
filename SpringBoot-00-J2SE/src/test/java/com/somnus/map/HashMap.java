package com.somnus.map;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HashMap {

    public static void main(String[] args) {

        java.util.HashMap<String, String> map = new java.util.HashMap<>();

        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    map.put(UUID.randomUUID().toString(), "");
                }
                System.out.println(Thread.currentThread().getName() + " put over");
            }).start();
        }

    }

    @Test
    public void test(){
        java.util.Map<String, List<String>> map = new java.util.HashMap<>();
        // ERROR:会有 NPE问题
        map.get("java框架").add("SpringBoot");
        // ERROR:会有 NPE问题, putIfAbsent返回旧值，如果没有则返回null
        /*map.putIfAbsent("java框架", new ArrayList<>()).add("NPE");*/
        map.computeIfAbsent("java框架", key -> new ArrayList<>()).add("SpringBoot");
        //computeIfAbsent:存在时返回存在的值，不存在时返回新值
        map.computeIfAbsent("java框架", key -> new ArrayList<>()).add("SpringCloud");
        map.computeIfAbsent("vue框架", key -> new ArrayList<>()).add("Element");
        System.out.println(map);

        System.out.println(map.getOrDefault("java框架", Lists.newArrayList()));
        System.out.println(map.getOrDefault("js框架", Lists.newArrayList()));
    }

}
