package com.somnus.springboot;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @author kevin.liu
 * @title: RedissonConfig
 * @projectName github
 * @description: TODO
 * @date 2021/3/9 20:04
 */
/*
@Configuration
public class RedissonConfig {

    @Primary
    @Bean(destroyMethod="shutdown")
    public RedissonClient redisson() throws IOException {
        RedissonClient redisson = Redisson.create(
                Config.fromYAML(new ClassPathResource("redisson.yaml").getInputStream()));
        return redisson;
    }
}
*/
