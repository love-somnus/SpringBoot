package com.somnus.springboot;

import cn.hutool.extra.spring.SpringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.boot.system.ApplicationTemp;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ConditionalTest {

    /** 父子容器都可以注入 */
    @Autowired
    private ApplicationContext applicationConnect;

    @Test
    public void conditionalOnBean(){
        Map<String, ApplicationHome> applicationHomes = applicationConnect.getBeansOfType(ApplicationHome.class);
        Map<String, ApplicationTemp> applicationTemps = applicationConnect.getBeansOfType(ApplicationTemp.class);
        Map<String, ApplicationPid>  applicationPids  = applicationConnect.getBeansOfType(ApplicationPid.class);

        System.out.println(applicationHomes);
        System.out.println(applicationTemps);
        System.out.println(applicationPids);
    }

    @Test
    public void conditionalOnBean2(){
        Map<String, ApplicationHome> applicationHomes = SpringUtil.getBeansOfType(ApplicationHome.class);
        Map<String, ApplicationTemp> applicationTemps = SpringUtil.getBeansOfType(ApplicationTemp.class);
        Map<String, ApplicationPid>  applicationPids  = SpringUtil.getBeansOfType(ApplicationPid.class);

        System.out.println(applicationHomes);
        System.out.println(applicationTemps);
        System.out.println(applicationPids);
    }


}
