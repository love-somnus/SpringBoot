package com.somnus.compare;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class CollectionsSortTest {

    @Test
    public void test1() {
        List<Integer> intList = Arrays.asList(4, 1, 3, -23);
        Collections.sort(intList);
        System.out.println(intList);
    }

    @Test
    public void test2() {
        List<String> strList = Arrays.asList("z", "a", "C");
        Collections.sort(strList);
        System.out.println(strList);
    }

    @Test
    public void test3() {
        List<String> strList = Arrays.asList("z", "a", "C");
        Collections.sort(strList, String.CASE_INSENSITIVE_ORDER);
        System.out.println(strList);
    }

    @Test
    public void test4() {
        List<String> strList = Arrays.asList("b", "o", "y");
        Collections.sort(strList, Collections.reverseOrder());
        System.out.println(strList);
    }

    @Test
    public void test5() {
        List<Integer> intList = Arrays.asList(88, 16, 27);
        Collections.sort(intList, Collections.reverseOrder());
        System.out.println(intList);
    }

    @Test
    public void test6() {
        List<People> pList = Arrays.asList(new People(3), new People(8), new People(6));
        Collections.sort(pList, new Comparator<People>() {
            @Override
            public int compare(People obj1, People obj2) {
                return obj1.age - obj2.age;
            }
        });
        System.out.println(pList);
    }

    @Test
    public void test7() {
        List<Person> pList = Arrays.asList(new Person(3), new Person(8), new Person(6));
        Collections.sort(pList);
        System.out.println(pList);
    }

}
