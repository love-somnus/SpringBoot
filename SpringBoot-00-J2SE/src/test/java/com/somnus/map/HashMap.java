package com.somnus.map;

import org.junit.Test;

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

    /**
     * put与compute
     * 相同：不论key是否存在，强制用value覆盖进去。
     * 区别：put返回旧value或null，compute返回新的value
     */
    @Test
    public void test1(){
        java.util.Map<String, List<String>> map = new java.util.HashMap<>(java.util.Map.of("java框架", List.of("SpringBoot", "SpringCloud")));
        //put:统一返回旧值，如果没有则返回null
        System.out.println(map.put("java框架", List.of("Dubbo")));
        System.out.println(map.put("js框架", List.of("jQuery")));
        System.out.println(map);
        //{java框架=[Dubbo], js框架=[jQuery]}
        System.out.println(map.put("key", null));//允许
        //{java框架=[Dubbo], key=null, js框架=[jQuery]}
        System.out.println(map);
    }
    @Test
    public void test2(){
        java.util.Map<String, List<String>> map = new java.util.HashMap<>(java.util.Map.of("java框架", List.of("SpringBoot", "SpringCloud")));
        //compute:统一返回新值, 新值并且一定会插入（但新值为null则空指针）
        System.out.println(map.compute("java框架", (k, v) -> List.of("Dubbo")));
        System.out.println(map.compute("js框架", (k, v) -> List.of("jQuery")));
        System.out.println(map);
        //{java框架=[Dubbo], js框架=[jQuery]}
        System.out.println(map.compute("key", null));//空指针
    }


    /**
     * putIfAbsent与computeIfAbsent：
     * 相同：key存在，则不操作，key不存在，则赋值一对新的（key，value）
     * 区别：putIfAbsent返回旧value或null，computeIfAbsent返回新的value
     *      putIfAbsent适合添加具有指定值的元素，而computeIfAbsent适合添加具有使用键计算的值的元素，key可参与计算。
     */
    @Test
    public void test1$(){
        java.util.Map<String, List<String>> map = new java.util.HashMap<>(java.util.Map.of("java框架", List.of("SpringBoot", "SpringCloud")));
        //putIfAbsent:统一返回旧值，如果没有则返回null
        //先计算value，再判断key是否存在，当Key存在的时候，如果Value获取比较昂贵的话，
        //putIfAbsent就白白浪费时间在获取这个昂贵的Value上(不会替换旧值)
        System.out.println(map.putIfAbsent("java框架", List.of("Dubbo")));
        System.out.println(map.putIfAbsent("js框架", List.of("jQuery")));
        //{java框架=[SpringBoot, SpringCloud], js框架=[jQuery]}
        System.out.println(map);
        System.out.println(map.putIfAbsent("key", null));//允许
        //{java框架=[SpringBoot, SpringCloud], key=null, js框架=[jQuery]}
        System.out.println(map);
    }

    @Test
    public void test2$(){
        java.util.Map<String, List<String>> map = new java.util.HashMap<>(java.util.Map.of("java框架", List.of("SpringBoot", "SpringCloud")));
        //computeIfAbsent:存在时返回存在的值，不存在时返回新值（但新值为null则空指针）
        //先判断key是否存在，存在则不执行value计算方法，不存在才执行
        System.out.println(map.computeIfAbsent("java框架", k -> List.of("Dubbo")));
        System.out.println(map.computeIfAbsent("js框架", k -> List.of("jQuery")));
        //{java框架=[SpringBoot, SpringCloud], js框架=[jQuery]}
        System.out.println(map);
        System.out.println(map.computeIfAbsent("key", null));;//空指针
    }

}
