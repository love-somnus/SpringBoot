package com.somnus.springboot.dubbo.service.user.provider.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.somnus.springboot.dubbo.service.user.api.UserService;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2018/12/8 12:41
 */
@Service(version = "${user.service.version}")
public class UserServiceImpl implements UserService {

    @Value("${dubbo.protocol.port}")
    private String port;

    @Override
    public String sayHi() {
        return "Hello Dubbo , i am from port:" + port;
    }
}
