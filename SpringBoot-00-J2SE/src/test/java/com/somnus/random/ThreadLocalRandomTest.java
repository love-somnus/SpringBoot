package com.somnus.random;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

public class ThreadLocalRandomTest {
    @Test
    public void test1() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        System.out.println(random.nextInt(10));

        System.out.println(random.nextInt(10));

        System.out.println(random.nextInt(10));
    }

    @Test
    public void test2() {
        System.out.println(ThreadLocalRandom.current().nextInt(20,30));

        System.out.println(ThreadLocalRandom.current().nextInt(10));

        System.out.println(ThreadLocalRandom.current().nextInt(10));
    }

    public static void main(String[] args) {
        ExecutorService taskPool = Executors.newCachedThreadPool();

        for (int i = 0; i < 100; i++) {
            final int taskID = i;
            taskPool.submit(new Runnable() {

                @Override
                public void run() {
                    StopWatch sw = new StopWatch();
                    sw.start();

                    for (int j = 0; j < 10000000; j++) {
                        ThreadLocalRandom.current().nextInt(10);
                    }
                    sw.stop();

                    System.out.println(Thread.currentThread().getName() + " >>>> " + taskID + " >>>> " + sw.getTime());
                }

            });
        }

        taskPool.shutdown();
    }

}
