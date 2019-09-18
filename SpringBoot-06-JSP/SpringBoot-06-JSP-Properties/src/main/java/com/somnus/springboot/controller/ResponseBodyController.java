package com.somnus.springboot.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2018/12/5 19:37
 */
@RestController
public class ResponseBodyController {

    @PostMapping(value="responsebodybind")
    public Account responsebodybind(Account account){
        System.out.println("responsebodybind:" + account);
        return account;
    }

    @PostMapping(value="responsebodybind2")
    public Account responsebodybind2(String username,String password){
        Account account = new Account(username,password);
        System.out.println("responsebodybind2:" + account);
        return account;
    }

    @Data
    @AllArgsConstructor
    public static class Account{

        private String username;

        private String password;

    }
}
