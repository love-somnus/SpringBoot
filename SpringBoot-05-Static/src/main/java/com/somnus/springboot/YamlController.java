package com.somnus.springboot;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kevin
 * @packageName com.somnus.springboot
 * @title: YamlController
 * @description: TODO
 * @date 2019/5/30 17:38
 */
@RestController
public class YamlController {

    @Autowired
    private Environment env;

    @Autowired
    private Person person;

    @GetMapping("yaml")
    public Person yaml(){
        return person;
    }


    @RequestMapping("test")
    @SneakyThrows
    public String test(HttpServletRequest request){
        System.out.println(IOUtils.toString(request.getInputStream(), "UTF-8"));
        return "ok";
    }

    @GetMapping("name")
    public String name(){
        String lastName = env.getProperty("person.lastName");
        return lastName;
    }

    @GetMapping("list")
    public List<String> list(){
        /*List<String> list = env.getProperty("person.lists" , ArrayList.class);*/
        /*List<String> list = env.getProperty("person.lists2" , ArrayList.class);*/
        /** 上面两种方式，使用env.getProperty是无效的*/
        List<String> list = env.getProperty("person.lists3" , ArrayList.class);
        return list;
    }
}
