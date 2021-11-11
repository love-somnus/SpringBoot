package com.somnus.springboot.controller;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("webflux")
public class WebFluxController {
    
    @GetMapping(value="flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> webflux(){
        return Flux.range(1, 100)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i -> System.out.println("processing count in stream flow :" + i))
                .map(i -> "0" + i);
    }
    
}
