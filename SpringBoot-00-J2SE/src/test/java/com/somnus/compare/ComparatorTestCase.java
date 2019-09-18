package com.somnus.compare;

import java.util.TreeSet;
import java.util.Comparator;

public class ComparatorTestCase {

    public static void main(String[] args) {

        TreeSet<People> set = new TreeSet<>(new Comparator<People>() {
            @Override
            public int compare(People obj1, People obj2) {
                return obj1.age - obj2.age;
            }
        });
        set.add(new People(1));
        set.add(new People(3));
        set.add(new People(8));
        set.add(new People(6));
        set.add(new People(1));

        System.out.println(set);
    }

}

class People {

    public int age;

    public People(int age) {
        super();
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "person age:" + this.age;
    }

}
