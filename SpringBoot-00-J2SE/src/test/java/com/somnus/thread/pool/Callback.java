package com.somnus.thread.pool;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class Callback {

    @Test
    public void futureTask() throws Exception {
        /* 创建单个线程的线程池，如果当前线程在执行任务时突然中断，则会创建一个新的线程替代它继续执行任务 */
        ExecutorService executor = Executors.newSingleThreadExecutor();

        FutureTask<Integer> future = new FutureTask<Integer>(() -> {
            int sum = 0;
            for (int j = 1; j <= 10; j++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100));// 为了测试出效果，让每次任务执行都需要一定时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " is looping of " + j);
                sum += j;
            }
            return sum;
        });

        executor.execute(future);

        System.out.println("你走你的，我先走一步");
        Thread.sleep(10000);//模拟业务逻辑也在做自己的事情，同时开工

        int result = future.get(5000, TimeUnit.MILLISECONDS); //取得结果，同时设置超时执行时间为5s

        System.out.println(result);

        executor.shutdown();// 任务执行完毕，关闭线程池
    }

    @Test
    public void future() throws Exception {
        /* 创建单个线程的线程池，如果当前线程在执行任务时突然中断，则会创建一个新的线程替代它继续执行任务 */
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Integer> future = executor.submit(() -> {
            int sum = 0;
            for (int j = 1; j <= 10; j++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100));// 为了测试出效果，让每次任务执行都需要一定时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " is looping of " + j);
                sum += j;
            }
            return sum;
        });

        System.out.println("你走你的，我先走一步");
        Thread.sleep(10000);//模拟业务逻辑也在做自己的事情，同时开工

        int result = future.get(5000, TimeUnit.MILLISECONDS); //取得结果，同时设置超时执行时间为5s

        System.out.println(result);//

        executor.shutdown();// 任务执行完毕，关闭线程池
    }

    /*************************************提交多个任务**********************************************/
    @Test
    public void total() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 1; i <= 10; i++) {
            final int taskID = i;
            FutureTask<Integer> future = new FutureTask<Integer>(() -> {
                int sum = 0;
                for (int j = 1; j <= 10; j++) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(new Random().nextInt(100));// 为了测试出效果，让每次任务执行都需要一定时间
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " is looping of " + j + " for task of " + taskID);
                    sum += j;
                }
                return sum + taskID;
            });
            executor.execute(future);
            //会阻塞在这里，想获取每个任务的结果，不推进使用这种方式，使用CompletionService更合适
            System.out.println(future.get(5000, TimeUnit.MILLISECONDS));
        }

        // 所有任务已经完成,关闭线程池
        System.out.println("执行完毕....");
        executor.shutdown();
    }

    @Test
    public void sum() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(100);

        long result = 0L;

        final int groupNum = 1000000 / 100;

        for (int i = 1; i <= 100; i++) {
            final int start = (i - 1) * groupNum + 1;
            final int end = i * groupNum;
            FutureTask<Long> future = new FutureTask<Long>(() -> {
                Long sum = 0L;
                for (int j = start; j <= end; j++) {
                    System.out.println(Thread.currentThread().getName() + " is looping of " + j + " for start of " + start + " for end of " + end);
                    sum += j;
                }
                return sum;
            });
            executor.execute(future);

            result += future.get(5000, TimeUnit.MILLISECONDS);
        }

        // 所有任务已经完成,关闭线程池
        System.out.println("执行完毕...." + result);
        executor.shutdown();
    }

}
