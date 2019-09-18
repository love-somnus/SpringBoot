package com.somnus.thread.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *  * @description:
 *  * @author: Somnus
 *  * @version: 1.0
 *  * @createDate: 2017年12月30日 下午3:47:38
 *  * Modification  History:
 *  *    Date        Author        Version        Description
 *  * -----------------------------------------------------------------------------------
 *  * 2017年12月30日             Somnus         1.0             初始化
 *  * 有10个运动员赛跑，开跑之前，裁判需要等待10个运动员都准备好才能发令，并且10个运动员准备好之后也都需要等待裁判发令才能开跑。
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        // 用于判断发令之前运动员是否已经完全进入准备状态，需要等待10个运动员，所以参数为10
        final CountDownLatch runner = new CountDownLatch(10);
        // 用于判断裁判是否已经发令，只需要等待一个裁判，所以参数为1
        final CountDownLatch referee = new CountDownLatch(1);

        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 1; i <= 10; i++) {
            final int offset = i;
            executor.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
                    System.out.println("运动员【" + offset + "】准备完毕" + Thread.currentThread().getName() + "-->" + runner.getCount());
                    runner.countDown();
                    referee.await();
                    System.out.println("运动员【" + offset + "】开跑..." + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        runner.await();
        referee.countDown();
        System.out.println("裁判：所有运动员准备完毕，开始..." + "-->" + referee.getCount());

        executor.shutdown();
    }

}