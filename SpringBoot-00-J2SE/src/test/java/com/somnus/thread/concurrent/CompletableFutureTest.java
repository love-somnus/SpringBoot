package com.somnus.thread.concurrent;/**
 * ClassName: CompletableFutureTest <br/>
 * Description: <br/>
 * date: 2021/3/17 17:31<br/>
 *
 * @author kevin.liu<br />
 * @version
 * @since JDK 1.8
 */

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * @title: CompletableFutureTest
 * @projectName github
 * @description: TODO
 * @author kevin.liu
 * @date 2021/3/17 17:31
 */
public class CompletableFutureTest {

    @Test
    @SneakyThrows
    public void test(){
        // 创建异步执行任务:
        CompletableFuture<Double> cf = CompletableFuture.supplyAsync(CompletableFutureTest::fetchPrice);
        // 如果执行成功:
        cf.thenAccept((result) -> {
            System.out.println("price: " + result);
        });
        // 如果执行异常:
        cf.exceptionally((e) -> {
            e.printStackTrace();
            return null;
        });

        System.out.println("start ............");

        Thread.sleep(200);

    }

    @Test
    @SneakyThrows
    public void test2(){
        // 第一个任务:
        CompletableFuture<String> cfQuery = CompletableFuture.supplyAsync(() -> queryCode("中国石油"));
        // cfQuery成功后继续执行下一个任务:
        CompletableFuture<Double> cfFetch = cfQuery.thenApplyAsync(CompletableFutureTest::fetchPrice);
        // cfFetch成功后打印结果:
        cfFetch.thenAccept((result) -> System.out.println("price: " + result));
        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:
        Thread.sleep(2000);
    }

    static Double fetchPrice() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        if (Math.random() < 0.3) {
            throw new RuntimeException("fetch price failed!");
        }
        return 5 + Math.random() * 20;
    }

    static String queryCode(String name) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        return "601857";
    }

    static Double fetchPrice(String code) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        return 5 + Math.random() * 20;
    }
}
