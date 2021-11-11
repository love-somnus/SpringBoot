package com.somnus.springboot.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kevin.liu
 * @title: ConditionalConfiguration
 * @projectName github
 * @description: TODO
 * @date 2021/10/29 10:59
 */
@Configuration
/* 在指定的Configuration类之前加载 */
@AutoConfigureBefore(ConditionalConfiguration2.class)
public class ConditionalConfiguration {

    @Bean
    public ApplicationHome applicationHome(){
        System.out.println("①");
        return new ApplicationHome();
    }

    /* Spring容器中[存在]指定【class的实例对象】时，对应的配置才生效 */
    @Bean
    @ConditionalOnBean(ApplicationHome.class)
    public ApplicationPid applicationPid0(){
        return new ApplicationPid();
    }

    @Bean
    @ConditionalOnBean(name = "applicationHome")
    public ApplicationPid applicationPid0_(){
        return new ApplicationPid();
    }

    /* Spring容器中[不存在]指定【class的实例对象】时，对应的配置才生效 */
    @Bean
    @ConditionalOnMissingBean(BasicJsonParser.class)
    public ApplicationPid applicationPid1(){
        return new ApplicationPid();
    }

    @Bean
    @ConditionalOnMissingBean(name = "applicationHome")
    public ApplicationPid applicationPid1_(){
        return new ApplicationPid();
    }

    /* *************************************************************************************************************** */

    /* 应用中[包含]某个类时，对应的配置才生效 */
    @Bean
    @ConditionalOnClass(ApplicationHome.class)
    public ApplicationPid applicationPid2(){
        return new ApplicationPid();
    }

    /* 应用中[不包含]某个类时，对应的配置才生效 */
    @Bean
    @ConditionalOnMissingClass({"org.springframework.boot.system.Application"})
    public ApplicationPid applicationPid3(){
        return new ApplicationPid();
    }

    /* *************************************************************************************************************** */

    /* 指定参数的值如何要求时，对应的配置才生效 */
    @Bean
    @ConditionalOnProperty(prefix = "timer", name = "enabled", havingValue = "open")
    public ApplicationPid applicationPid4(){
        return new ApplicationPid();
    }

    /* 指定文件资源存在时，对应的配置才生效 */
    @Bean
    @ConditionalOnResource(resources = {"classpath:banner.txt"})
    public ApplicationPid applicationPid5(){
        return new ApplicationPid();
    }

    /* 指定参数的指符合要求时，对应的配置才生效，和ConditionalOnProperty的区别在于这个注解使用SpringEL表达式 */
    @Bean
    @ConditionalOnExpression("'${timer.enabled}'.equalsIgnoreCase('CLOSE')")
    public ApplicationPid applicationPid6(){
        return new ApplicationPid();
    }
}
