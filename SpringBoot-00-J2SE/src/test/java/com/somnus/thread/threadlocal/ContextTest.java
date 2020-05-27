package com.somnus.thread.threadlocal;

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class ContextTest {

    private int num = 0;

    @Test
    @SneakyThrows
    public void test() {
        CountDownLatch countDownLatch = new CountDownLatch(100);

        for (int j = 0; j < 100; j++) {
            new Thread(() -> {
                ThreadLocalContext.set("num" , 0);
                // 获取当前线程的本地变量，然后累加1000次
                Integer index = Integer.valueOf(ThreadLocalContext.get("num").toString());
                for (int i = 0; i < 1000; i++) {
                    index++;
                }
                // 重新设置累加后的本地变量
                ThreadLocalContext.set("num" , index);
                System.out.println(Thread.currentThread().getName() + " : " + index);
                ThreadLocalContext.remove();
                countDownLatch.countDown();
            }, "Thread-" + j).start();
        }

        //让主线程等待所有子线程完成 10000
        countDownLatch.await();
    }

    @Test
    @SneakyThrows
    public void test2() {
        CountDownLatch countDownLatch = new CountDownLatch(100);

        for (int j = 0; j < 100; j++) {
            new Thread(() -> {
                // 获取当前线程的本地变量，然后累加1000次
                for (int i = 0; i < 1000; i++) {
                    num++;
                }
                System.out.println(Thread.currentThread().getName() + " : " + num);
                ThreadLocalContext.remove();
                countDownLatch.countDown();
            }, "Thread-" + j).start();
        }

        //让主线程等待所有子线程完成 10000
        countDownLatch.await();
    }
}
