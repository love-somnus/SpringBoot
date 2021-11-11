package com.somnus.springboot.dubbo.service.user.provider.api.impl;

import com.somnus.springboot.dubbo.service.user.api.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2018/12/8 12:41
 */
@DubboService(version = "3.0.4", interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    /*@Value("${server.port}")*/
    private String port = "12";

    @Override
    public String sayHi() {
        return "Hello Dubbo , i am from port:" + port;
    }
}
