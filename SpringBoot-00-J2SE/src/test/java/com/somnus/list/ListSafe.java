package com.somnus.list;

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

/**
 * @author Somnus
 * @packageName com.somnus.java8
 * @title: FunctionTest
 * @description: TODO
 * @date 2019/11/7 16:09
 * @see [https://blog.csdn.net/u010416101/article/details/88720974]
 */
public class ListSafe {

    @Test
    @SneakyThrows
    public void arrayList(){
        List<Object> list = new ArrayList<>();

        CountDownLatch countDownLatch = new CountDownLatch(10000);

        for (int i = 0; i < 10000; i++) {

            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    list.add(new Object());
                    System.out.println("####");
                }
                countDownLatch.countDown();
            }, "Thread-" + i).start();
        }

        //让主线程等待所有子线程完成 10000
        countDownLatch.await();

        System.out.println(list.size());
    }

    @Test
    @SneakyThrows
    public void vector(){
        List<Object> list = new Vector<>();

        CountDownLatch countDownLatch = new CountDownLatch(10000);

        for (int i = 0; i < 10000; i++) {

            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    list.add(new Object());
                    System.out.println("####");
                }
                countDownLatch.countDown();
            }, "Thread-" + i).start();
        }

        //让主线程等待所有子线程完成 10000
        countDownLatch.await();

        System.out.println(list.size());
    }

    @Test
    @SneakyThrows
    public void synchronizedList(){
        List<Object> list = Collections.synchronizedList(new ArrayList<>());

        CountDownLatch countDownLatch = new CountDownLatch(10000);

        for (int i = 0; i < 10000; i++) {

            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    list.add(new Object());
                    System.out.println("####");
                }
                countDownLatch.countDown();
            }, "Thread-" + i).start();
        }

        //让主线程等待所有子线程完成 10000
        countDownLatch.await();

        System.out.println(list.size());
    }

    /** test中不用并发类辅助测试 是有问题的 如果子线程执行时间过长  部分线程根本不会得到执行，想让子线程都完整执行完成 使用并发类帮忙*/
    @Test
    public void synchronizedList2(){
        List<Object> list = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < 5; i++) {

            new Thread(() -> {
                for (int j = 0; j < 3; j++) {
                    list.add(new Object());
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("$$$$$$$$$$");
                }

            }, "Thread-" + i).start();
        }

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("#####################");
    }

    /** 测试的时候 如果不考虑需要子线程的计算结果 只需子线程和主线程都能得到完整的执行 考虑使用main  ，test会是一个坑*/
    public static void main(String[] args) {
        List<Object> list = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < 5; i++) {

            new Thread(() -> {
                for (int j = 0; j < 3; j++) {
                    list.add(new Object());
                    System.out.println("$$$$$$$$$$");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }, "Thread-" + i).start();
        }

        System.out.println("#####################");
    }

}
