package com.somnus.test;

import org.junit.Test;

import java.util.Random;

/**
 * @author Kevin
 * @packageName com.somnus.test
 * @title: MainTest
 * @description: TODO
 * @date 2019/11/7 17:35
 */
public class JuintTest2 {

    @Test
    public void test(){

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

        /** 主线程执行完毕后，如果子线程还没执行完成，就不会管的，测试的时候谨慎使用，main则不会*/
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("$$$$$$$$$$$$$$$$$$");

    }
}
