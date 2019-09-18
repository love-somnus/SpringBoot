package com.somnus.thread.concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class PhaserTest1 {

    public static void main(String[] args) {
        final Phaser phaser = new Phaser(10);

        ExecutorService executor = Executors.newCachedThreadPool();
        /** 模拟10个用户 **/
        for (int i = 1; i <= 10; i++) {
            final int offset = i;
            executor.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
                    System.out.println(Thread.currentThread().getName() + "->" + offset + "到达聚餐地点，当前还有" + (phaser.getUnarrivedParties() - 1) + "人未到达");
                    //阻塞
                    phaser.arriveAndAwaitAdvance();
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
                    System.out.println(Thread.currentThread().getName() + "->" + offset + "吃完饭了，准备回家。。。。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }
}