package com.somnus.test;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author Kevin
 * @packageName com.somnus.test
 * @title: MainTest
 * @description: TODO
 * @date 2019/11/7 17:35
 */
public class MainTest {

    @SneakyThrows
    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            new Thread(() ->  {
                System.out.println("##############");
                latch.countDown();
            }, "Thread-" + i).start();
        }

        //让主线程等待所有子线程完成 10
        latch.await();

        System.out.println("$$$$$$$$$$$$$$$$$$");

    }
}
