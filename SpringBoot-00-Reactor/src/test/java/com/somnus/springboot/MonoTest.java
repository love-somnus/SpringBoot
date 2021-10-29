package com.somnus.springboot;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.Stream;

/**
 * @author kevin.liu
 * @title: MonoTest
 * @projectName github
 * @description: TODO
 * @date 2021/10/24 14:16
 */
public class MonoTest {

    @Test
    public void just(){
        Mono.just("somnus").subscribe(System.out::println);
    }

    @Test
    public void error(){
        Mono.just("somnus")
                .then(Mono.error(new RuntimeException("Exception occured")))
                .log()
                .subscribe(System.out::println);
    }

    @Test
    public void flux(){

        Flux.just("somnus", "smile").subscribe(System.out::println);

        Flux.fromArray(new String[]{"apple", "pear"}).subscribe(System.out::println);

        Flux.fromStream(Stream.of("blue", "green")).subscribe(System.out::println);

        Flux.just("somnus", "smile")
                .concatWithValues("AWS")
                .concatWith(Mono.error(new RuntimeException("Exception occured in Flux")))
                .log()
                .subscribe(System.out::println);
    }

    @Test
    public void range(){

        Flux.range(1, 100)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println("processing count in stream flow :" + i))
                .map(i -> "0" + i)
                .subscribe(System.out::println);

        try {
            Thread.sleep(10000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
