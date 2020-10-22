package com.somnus.java8;

import java.util.concurrent.*;
import java.util.stream.IntStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StopWatch;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2019/1/10 14:33
 */
public class JDKTest {

    private StopWatch sw = new StopWatch();
    ExecutorService executor = Executors.newCachedThreadPool();

    @Before
    public void before() {
        sw.start();
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
    @After
    public void after() {
        sw.stop();
        System.out.println("共消耗：" + sw.getTotalTimeMillis());
    }
    @Test//17600 17409 17485
    public void parallel1(){
        IntStream.range(0, 10000).forEach(item ->{
            try{
                Thread.sleep(1L);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        });
    }

    @Test//150 153 155
    public void parallel1_(){
        IntStream.range(0, 10000).forEach(item ->{
            executor.execute(() -> {
                try{
                    Thread.sleep(1L);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            });
        });
    }

    @Test//2468 2531 2550
    public void parallel2(){
        IntStream.range(0, 10000).parallel().forEach(item ->{
            try{
                Thread.sleep(1L);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        });
    }

    @Test//133 124 128
    public void parallel2_(){
        IntStream.range(0, 10000).parallel().forEach(item ->{
            executor.execute(() -> {
                try{
                    Thread.sleep(1L);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
            });
        });
    }

}

