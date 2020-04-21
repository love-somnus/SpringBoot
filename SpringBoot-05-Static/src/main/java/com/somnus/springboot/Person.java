package com.somnus.springboot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Kevin
 * @packageName com.somnus.springboot
 * @title: Person
 * @description: TODO
 * @date 2019/5/30 17:35
 */
@Data
@Component
@ConfigurationProperties(prefix = "person")
public class Person {
    private String lastName;
    private Integer age;
    private Boolean boss;
    private Date birth;
    private Map<String, Object> maps;
    private Map<String, Object> maps2;
    private List<Object> lists;
    private List<Object> lists2;
    private List<Object> lists3;
    private Dog dog;
    private List<Map<String,String>> listmap;
    private List<Flower> flowers;
}
@Data
class Flower{
    private String name;

    private String color;
}