package com.somnus.compare;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.junit.Test;

public class ArraySortTest {

    @Test
    public void test1() {
        int[] intArray = new int[]{4, 1, 3, -23};
        Arrays.sort(intArray);
        System.out.println(Arrays.toString(intArray));
    }

    @Test
    public void test2() {
        String[] strArray = new String[]{"z", "a", "C"};
        Arrays.sort(strArray);
        System.out.println(Arrays.toString(strArray));
    }

    @Test
    public void test3() {
        String[] strArray = new String[]{"z", "a", "C"};
        Arrays.sort(strArray, String.CASE_INSENSITIVE_ORDER);
        System.out.println(Arrays.toString(strArray));
    }

    @Test
    public void test4() {
        String[] strArray = new String[]{"b", "o", "y"};
        Arrays.sort(strArray, Collections.reverseOrder());
        System.out.println(Arrays.toString(strArray));
    }

    @Test
    public void test5() {
        Integer[] intArray = new Integer[]{88, 16, 27};
        Arrays.sort(intArray, Collections.reverseOrder());
        System.out.println(Arrays.toString(intArray));
    }

    @Test
    public void test6() {
        People[] pArray = {new People(6), new People(8), new People(6)};
        Arrays.sort(pArray, new Comparator<People>() {
            @Override
            public int compare(People obj1, People obj2) {
                return obj1.age - obj2.age;
            }
        });
        System.out.println(Arrays.toString(pArray));
    }

    @Test
    public void test7() {
        Person[] pArray = {new Person(6), new Person(8), new Person(6)};
        Arrays.sort(pArray);
        System.out.println(Arrays.toString(pArray));
    }

}