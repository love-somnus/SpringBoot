package com.somnus.java8;

/**
 * @author Kevin
 * @packageName com.somnus.java8
 * @title: Functional
 * @description: 函数式接口 (Functional Interface) 就是一个有且仅有一个抽象方法，但是可以有多个非抽象方法的接口。
 * @date 2020/5/14 16:59
 */
public class FunctionalInterfacee{

    public static void main(String[] args) {
        FunctionalInterfacee facee = new FunctionalInterfacee();

        facee.printCurrentTime(() -> System.out.println(System.currentTimeMillis()));

        facee.sound(10, val -> System.out.println("报数：" + val) );

        /** 静态方法也是可以的*/
        System.out.println(FunctionalInterfacee.math(10, 20, (a,b) -> a + b ));
    }

    void printCurrentTime(Func0 f){
        f.printCurrentTime();
    }
    void sound(int a, Func1 f){
        f.sound(a);
    }
    static int math(int a, int b, Func2 f){
        return f.math(a, b);
    }
}
@FunctionalInterface
interface Func0 {
    void printCurrentTime();
}

@FunctionalInterface
interface Func1 {
    void sound(int a);
}

@FunctionalInterface
interface Func2 {
    int math(int a, int b);
}
