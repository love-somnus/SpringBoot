package com.somnus.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy=true)
@MapperScan(basePackages = "com.somnus.springboot.mybatis.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}