package com.somnus.compare;

import java.util.TreeSet;

public class ComparableTestCase {

    public static void main(String[] args) {
        /**
         * TreeSet底层其实是一个二叉树机构，且每插入一个新元素(第一个除外)
         * 都会调用compareTo()方法去和上一个插入的元素作比较，并按二叉树的结构进行排列
         *
         * 返回值写为0，元素值每次比较，都认为是相同的元素，这时就不再向TreeSet中插入除第一个外的新元素。
         * 所以TreeSet中就只存在插入的第一个元素。
         */
        TreeSet<Person> set = new TreeSet<>();
        set.add(new Person(1));
        set.add(new Person(3));
        set.add(new Person(8));
        set.add(new Person(6));
        set.add(new Person(1));

        System.out.println(set);
    }

}

class Person implements Comparable<Person> {

    public int age;

    @Override
    public int compareTo(Person o) {
        return this.age - o.age;
    }

    public Person(int age) {
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