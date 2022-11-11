package com.somnus.thread;

import cn.hutool.core.thread.ExecutorBuilder;
import org.junit.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author kevin.liu
 * @date 2022/10/29 12:47
 */
public class CompeleteFuture {

    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println("当前线程数" + ForkJoinPool.commonPool().getPoolSize());
        System.out.println("最大线程数" + ForkJoinPool.getCommonPoolParallelism());
    }

    @Test
    public void supplyAsync(){
        printTimeAndThread("进入厨房");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            printTimeAndThread("炒菜");
            delay();
            return "做好了西红柿炒蛋";
        });
        printTimeAndThread(future.join());
    }

    @Test
    public void thenCompose(){
        printTimeAndThread("进入厨房");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            printTimeAndThread("洗菜");
            delay(2);
            return "西红柿";
        }).thenCompose(dish -> CompletableFuture.supplyAsync(() -> {
            printTimeAndThread("打蛋");
            delay(1);
            return "做好了" + dish + "炒鸡蛋";
        }));
        printTimeAndThread(future.join());
    }

    @Test
    public void thenComposeAsync(){
        printTimeAndThread("进入厨房");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            printTimeAndThread("洗菜");
            delay(2);
            return "西红柿";
        }).thenComposeAsync(dish -> CompletableFuture.supplyAsync(() -> {
            printTimeAndThread("打蛋");
            delay(1);
            return "做好了" + dish + "炒鸡蛋";
        }));
        printTimeAndThread(future.join());
    }

    @Test
    public void thenApply(){
        printTimeAndThread("进入厨房");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            printTimeAndThread("洗菜");
            delay();
            return "西红柿";
        }).thenApply(dish -> {
            printTimeAndThread("打蛋");
            delay();
            return "做好了" + dish + "炒鸡蛋";
        });
        printTimeAndThread(future.join());
    }

    @Test
    public void thenApplyAsync(){
        printTimeAndThread("进入厨房");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            printTimeAndThread("洗菜");
            delay();
            return "西红柿";
        }).thenApplyAsync(dish -> {
            printTimeAndThread("打蛋");
            delay();
            return "做好了" + dish + "炒鸡蛋";
        });
        printTimeAndThread(future.join());
    }

    @Test
    public void thenCombine(){
        printTimeAndThread("进入厨房");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            printTimeAndThread("炒菜");
            delay();
            return "做好了西红柿炒蛋";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            printTimeAndThread("淘米");
            delay();
            return  "煮熟了米饭";
        }), (dish, rice) -> {
            delay();
            return String.format("%s + %s", dish, rice);
        });
        printTimeAndThread(future.join());
    }

    @Test
    public void thenCombineAsync(){
        printTimeAndThread("进入厨房");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            printTimeAndThread("炒菜");
            delay();
            return "做好了西红柿炒蛋";
        }).thenCombineAsync(CompletableFuture.supplyAsync(() -> {
            printTimeAndThread("淘米");
            delay();
            return  "煮熟了米饭";
        }), (dish, rice) -> {
            delay();
            return String.format("%s + %s", dish, rice);
        });
        printTimeAndThread(future.join());
    }

    /**
     * 两个任务一起运行，哪个先完成就把哪个交给function
     */
    @Test
    public void applyToEither(){
        printTimeAndThread("步行进入公交站");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            printTimeAndThread("808公交车正在赶来");
            delay();
            return "808公交车到站";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            printTimeAndThread("101公交车正在赶来");
            delay();
            return "101公交车到站";
        }), first -> first);

        printTimeAndThread(String.format("%s, 上车吧", future.join()));
    }

    @Test
    public void exceptionally(){
        printTimeAndThread("步行进入公交站");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            printTimeAndThread("208公交车正在赶来");
            delay();
            return "208公交车到站";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            printTimeAndThread("101公交车正在赶来");
            delay();
            return "101公交车到站";
        }), first -> {
            printTimeAndThread(first);
            int no = new Random().nextInt(3);
            if(first.startsWith(String.valueOf(no))){
                throw new IllegalStateException("撞到站台旁边的行人了");
            }
            return first;
        }).exceptionally(e -> {
            printTimeAndThread(e.getMessage());
            printTimeAndThread("叫网约车");
            delay();
            return "网约车到站";
        });

        printTimeAndThread(String.format("%s, 上车吧", future.join()));
    }

    /**
     * 因为A还有空闲，B上去，不会再开新线程
     */
    @Test
    public void thenCompose2(){
        printTimeAndThread("进入厨房");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            printTimeAndThread("洗菜");
            delay(2);
            return "西红柿";
        }).thenCompose(dish -> {
            printTimeAndThread("鸡蛋用光了，楼下小店买一点");
            return CompletableFuture.supplyAsync(() -> {
                printTimeAndThread("打蛋");
                delay(1);
                return "做好了" + dish + "炒鸡蛋";
            });
        });
        printTimeAndThread(future.join());
    }

    @Test
    public void thenComposeAsync2(){
        printTimeAndThread("进入厨房");
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            printTimeAndThread("洗菜");
            delay(2);
            return "西红柿";
        }).thenComposeAsync(dish -> {
            printTimeAndThread("鸡蛋用光了，楼下小店买一点");
            return CompletableFuture.supplyAsync(() -> {
                printTimeAndThread("打蛋");
                delay(1);
                return "做好了" + dish + "炒鸡蛋";
            });
        });
        printTimeAndThread(future.join());
    }

    /**
     * 使用ExecutorService executor
     * 不可以省写CompletableFuture.allOf
     * 正确的写法参考allOf
     * 当然也可以使用并行流 意义不大
     */
    @Test
    public void supplyAsyncVs2(){
        long begin = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(100);
        List<Integer> collect = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> CompletableFuture.supplyAsync(CompeleteFuture::delay, executor))
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        System.out.println("最终耗时" + (System.currentTimeMillis() - begin) + "毫秒");
        System.out.println(collect);
    }

    /**
     * 获取率先完成的任务结果——anyOf
     */
    @Test
    public void anyOf(){
        long begin = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(100);

        List<CompletableFuture<Integer>> futures = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> CompletableFuture.supplyAsync(CompeleteFuture::delay, executor)).toList();

        Object collect = CompletableFuture.anyOf(futures.toArray(new CompletableFuture[]{})).join();

        System.out.println("最终耗时" + (System.currentTimeMillis() - begin) + "毫秒");

        System.out.println(collect);
    }

    /**
     * 获取所有完成结果——allOf
     */
    @Test
    public void allOf(){
        long begin = System.currentTimeMillis();

        ExecutorService executor = ExecutorBuilder.create().setCorePoolSize(5).setMaxPoolSize(100).useSynchronousQueue().build();

        List<CompletableFuture<Integer>> futures = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> CompletableFuture.supplyAsync(CompeleteFuture::delay, executor)).toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[]{})).join();

        List<Integer> collect = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());

        System.out.println("最终耗时" + (System.currentTimeMillis() - begin) + "毫秒");

        System.out.println(collect);
    }

    /**
     * 获取所有完成结果——allOf
     */
    @Test
    public void allOf_(){
        long begin = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(100);

        List<CompletableFuture<Integer>> futures = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> CompletableFuture.supplyAsync(CompeleteFuture::delay, executor)).toList();

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[]{}));

        CompletableFuture<List<Integer>> collect = allOf.thenApply(t -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));

        System.out.println("最终耗时" + (System.currentTimeMillis() - begin) + "毫秒");

        System.out.println(collect.join());

        System.out.println("最终耗时" + (System.currentTimeMillis() - begin) + "毫秒");
    }

    /**
     * 如果list比较大的话，建议使用自定义线程池
     * 参考allOf
     */
    @Test
    public void supplyAsyncVs(){
        long begin = System.currentTimeMillis();

        List<CompletableFuture<Integer>> futures = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> CompletableFuture.supplyAsync(CompeleteFuture::delay)).toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[]{})).join();

        List<Integer> collect = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());

        System.out.println("最终耗时" + (System.currentTimeMillis() - begin) + "毫秒");

        System.out.println(collect);
    }

    @Test
    public void parallel(){
        long begin = System.currentTimeMillis();
        List<Integer> collect = IntStream.rangeClosed(1, 100).parallel()
                .mapToObj(i -> delay())
                .collect(Collectors.toList());
        System.out.println("最终耗时" + (System.currentTimeMillis() - begin) + "毫秒");
        System.out.println(collect);
    }

    private static void printTimeAndThread(String tag){
        String result = new StringJoiner("\t|\t")
                .add(LocalTime.now().toString())
                .add(String.valueOf(Thread.currentThread().getId()))
                .add(Thread.currentThread().getName())
                .add(tag)
                .toString();
        System.out.println(result);
    }

    private static Integer delay(){
        int timeout = new Random().nextInt(1, 3);
        try {
            // 每个依次睡眠1s，模拟线程耗时
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return timeout;
    }

    private static void delay(int timeout){
        try {
            // 每个依次睡眠1s，模拟线程耗时
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
