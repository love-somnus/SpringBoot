package com.somnus.random;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.time.StopWatch;

public class Random2Test {

    private static ThreadLocal<Random> random = ThreadLocal.withInitial(() -> new Random());

    public static void main(String[] args) {
        ExecutorService taskPool = Executors.newCachedThreadPool();

        final CountDownLatch latch = new CountDownLatch(2);

        for (int i = 0; i < 100; i++) {
            final int taskID = i;
            taskPool.submit(() ->{
                try {
                    latch.countDown();
                    latch.await();

                    StopWatch sw = new StopWatch();
                    sw.start();

                    for (int j = 0; j < 10000000; j++) {
                        random.get().nextInt(10);
                    }
                    sw.stop();

                    random.remove();

                    System.out.println(Thread.currentThread().getName() + " >>>> " + taskID + " >>>> " + sw.getTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        taskPool.shutdown();
    }

}
