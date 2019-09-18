package com.somnus.lombok;

import lombok.SneakyThrows;
import java.io.UnsupportedEncodingException;
/**
 * @author Kevin
 * @packageName com.somnus.lombok
 * @title: SneakyThrowsExample
 * @description: TODO
 * @date 2019/5/24 9:24
 */
public class SneakyThrowsExample {

    @SneakyThrows(UnsupportedEncodingException.class)
    public static void main(String[] args) {
        String str = new String("123".getBytes(), "UTF-8");
        System.out.println(str);
    }
}
