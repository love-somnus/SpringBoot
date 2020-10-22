package com.somnus.java8.lambda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.assertj.core.util.Lists;

import java.util.List;

/**
 * @author Kevin
 * @packageName com.somnus.java8.lambda
 * @title: Apple
 * @description: TODO
 * @date 2020/7/6 14:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fruit {

    private String alias;

    private String name;

    private Integer num;

    public Fruit(String alias, String name) {
        this.alias = alias;
        this.name = name;
    }

    public Fruit(String name, Integer num) {
        this.name = name;
        this.num = num;
    }

    public Fruit(Integer num) {
        this.num = num;
    }
}
