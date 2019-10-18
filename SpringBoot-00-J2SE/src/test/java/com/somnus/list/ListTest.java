package com.somnus.list;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Somnus
 * @packageName com.somnus.list
 * @title: ListTest
 * @description: TODO
 * @date 2019/10/18 11:27
 */
public class ListTest {

    @Test
    public void size(){
        int[] intArray = {1, 2, 3};
        List list2 = Arrays.asList(intArray);
        System.out.println(list2.size());

        // Java 8
        List list3 = Arrays.stream(intArray).boxed().collect(Collectors.toList());
        System.out.println(list3.size());

        //使用包装类数组
        List<Integer> list = Arrays.asList(1, 2, 3);
        System.out.println(list.size());
    }

    @Test
    public void add(){
        //返回一个由指定数组生成的固定大小的 List
        List<String> list = Arrays.asList("Apple", "Banana", "Orange");
        //不支持动态扩容
        list.add("purple");
    }

    @Test
    public void add2(){
        List<String> list = new ArrayList<>(Arrays.asList("Apple", "Banana", "Orange"));
        list.add("purple");
    }
}
