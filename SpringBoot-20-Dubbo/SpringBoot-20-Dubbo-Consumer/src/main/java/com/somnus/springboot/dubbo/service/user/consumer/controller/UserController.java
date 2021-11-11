package com.somnus.springboot.dubbo.service.user.consumer.controller;

import com.somnus.springboot.dubbo.service.user.api.UserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2018/12/8 12:54
 */
@RestController
public class UserController {

    @DubboReference(version = "3.0.4")
    private UserService userService;

    @RequestMapping(value = "hi")
    public String sayHi() {
        return userService.sayHi();
    }
}
