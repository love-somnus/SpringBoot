package com.somnus.test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Kevin
 * @packageName com.somnus.test
 * @title: MainTest
 * @description: TODO
 * @date 2019/11/7 17:35
 */
public class MainTest2 {

    public static void main(String[] args){

        for (int i = 0; i < 10; i++) {

            new Thread(() -> {
                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("##############");
            }, "Thread-" + i).start();

        }

        /** 主线程和子线程相互独立，主线程执行完毕后，还会让子线程继续执行*/
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("$$$$$$$$$$$$$$$$$$");

    }
}
