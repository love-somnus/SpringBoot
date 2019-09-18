package com.somnus.springboot.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2018/12/5 19:33
 */
@RestController
public class RequestBodyController {

    @PostMapping(value="requestbodybind")
    public Account requestBodyBind(@RequestBody Account account){
        System.out.println("requestbodybind:" + account);
        return account;
    }

    @PostMapping(value="requestbodybind2")
    public Account requestbodybind(String username,String password){
        Account account = new Account(username,password);
        System.out.println("requestbodybind2:" + account);
        return account;
    }

    @Data
    @AllArgsConstructor
    public static class Account{

        private String username;

        private String password;

    }
}
