package com.somnus.springboot;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.zookeeper.config.CuratorFrameworkFactoryBean;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;

/**
 * @author kevin.liu
 * @title: ZookeeperLockConfiguration
 * @projectName SpringBoot
 * @description: TODO
 * @date 2021/12/15 13:48
 */
@Configuration
public class ZookeeperLockConfiguration {

    @Value("${zookeeper.host}")
    private String zkUrl;

    @Bean
    public CuratorFrameworkFactoryBean curatorFrameworkFactoryBean(){
        return new CuratorFrameworkFactoryBean(zkUrl);
    }

    @Bean
    public ZookeeperLockRegistry zookeeperLockRegistry(CuratorFramework curatorFramework){
        return new ZookeeperLockRegistry(curatorFramework,"/zookeeper-lock");
    }
}
