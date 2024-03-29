package com.somnus.reflect.reflect;

import java.lang.reflect.Field;

/**
 * String对象真的不可变吗
 *
 * @author Somnus
 * @version V1.0
 * @Title: PrivateProperty.java
 * @Package com.somnus.reflect.reflect
 * @Description: TODO
 * @date 2015年6月10日 上午9:28:56
 */
public class PrivateProperty {

    public static void main(String[] args) throws Exception {

        //创建字符串"Hello World"， 并赋给引用s
        String s = "Hello World";

        System.out.println("s = " + s); //Hello World

        //获取String类中的value字段
        Field valueFieldOfString = String.class.getDeclaredField("value");

        //改变value属性的访问权限
        valueFieldOfString.setAccessible(true);

        //获取s对象上的value属性的值
        char[] value = (char[]) valueFieldOfString.get(s);

        //改变value所引用的数组中的第5个字符
        value[5] = '_';

        System.out.println("s = " + s);  //Hello_World
    }

}
