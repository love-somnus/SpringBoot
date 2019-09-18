package com.somnus.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ArrayListSafe {

    public static void main(String[] args) throws InterruptedException {
        List<Object> list = new ArrayList<>();//new Vector<>()

        int threadCount = 1000;

        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {

            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    list.add(new Object());
                }

            }, "Thread-" + i).start();

            countDownLatch.countDown();
        }

        //让主线程等待所有子线程完成 1000*100
        countDownLatch.await();

        System.out.println(list.size());

    }

}
