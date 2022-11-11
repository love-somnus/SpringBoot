package com.somnus.thread.pool;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *  * @description:
 *  * @author: Somnus
 *  * @version: 1.0
 *  * @createdate: 2017年4月4日 下午11:21:25
 *  * Modification  History:
 *  *    Date        Author        Version        Description
 *  * -----------------------------------------------------------------------------------
 *  * 2017年4月4日             Somnus         1.0             初始化
 */
public class ScheduledThreadPool {

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        // 5秒后执行任务  
        /*executor.schedule(() -> System.out.println("爆炸"), 5, TimeUnit.SECONDS);*/

        System.out.println("scheduleAtFixedRate 方法添加任务：" + LocalDateTime.now());
        executor.scheduleAtFixedRate((Runnable) () -> {
            System.out.println("执行 scheduleAtFixedRate 方法：" + LocalDateTime.now());
            // 休眠 2s
            try {
                TimeUnit.SECONDS.sleep(3);//这里面的任务时间如果大于间隔时间，则按这里面的来|如果小于间隔时间，则按间隔时间来
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },
        5L, // 5s 后开始执行定时任务
        2L, // 定时任务的执行间隔为 2s
        TimeUnit.SECONDS); // 描述上面两个参数的时间单位


        /*System.out.println("scheduleWithFixedDelay 方法添加任务：" + LocalDateTime.now());
        executor.scheduleWithFixedDelay(() -> {
            System.out.println("执行 scheduleWithFixedDelay 方法：" + LocalDateTime.now());
            // 休眠 2s
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },
        3L, // 3s 后开始执行定时任务
        1L, // 定时任务执行完 2s 之后，再执行下一个定时任务
        TimeUnit.SECONDS); // 描述上面两个参数的时间单位*/
    }
}