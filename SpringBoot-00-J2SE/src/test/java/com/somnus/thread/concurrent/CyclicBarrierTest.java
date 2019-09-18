package com.somnus.thread.concurrent;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *  * @description:
 *  * @author: Somnus
 *  * @version: 1.0
 *  * @createDate: 2017年12月29日 下午11:16:38
 *  * Modification  History:
 *  *    Date        Author        Version        Description
 *  * -----------------------------------------------------------------------------------
 *  * 2017年12月29日             Somnus        1.0          可循环的障碍物
 *  * 公司组织周末聚餐吃饭，首先各自从家里到聚餐地点，全部到齐之后，才开始一起吃东西（同步点），
 *  * 假如人员没到齐，到的人只能等待在那里（阻塞），直到所有人到齐之后才开始吃饭。
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        /** 定义窗口个数 **/
        final CyclicBarrier cb = new CyclicBarrier(10, new Runnable() {

            @Override
            public void run() {
                System.out.println("人员全部到齐了，各自拍照留念，拍照结束，就开始吃饭。。。。");
            }

        });

        ExecutorService executor = Executors.newCachedThreadPool();
        /** 模拟10个用户 **/
        for (int i = 1; i <= 10; i++) {
            final int offset = i;
            executor.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
                    System.out.println(Thread.currentThread().getName() + "->" + offset + "到达聚餐地点，当前已有" + (cb.getNumberWaiting() + 1) + "人到达");
                    //阻塞
                    cb.await();
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
                    System.out.println(Thread.currentThread().getName() + "->" + offset + "吃完饭了，准备回家。。。。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }

}