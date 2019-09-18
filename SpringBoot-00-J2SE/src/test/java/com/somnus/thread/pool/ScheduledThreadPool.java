package com.somnus.thread.pool;

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
        executor.schedule(() -> System.out.println("爆炸"), 5, TimeUnit.SECONDS);

        // 5秒后执行任务，以后每2秒执行一次
        executor.scheduleAtFixedRate(() -> System.out.println("#爆炸#"), 5, 2, TimeUnit.SECONDS);
    }
}