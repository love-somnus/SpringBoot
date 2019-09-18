package com.somnus.reflect.reflect;

import java.lang.reflect.Method;

/**
 * 反射操作私有方法
 *
 * @author Somnus
 * @version V1.0
 * @Title: PrivateMethod.java
 * @Package com.somnus.reflect.reflect
 * @Description: TODO
 * @date 2015年6月10日 上午9:31:36
 */
public class PrivateMethod {

    public static void main(String[] args) throws Exception {

        Private p1 = new Private();

        Class<?> clazz = p1.getClass();

        Method method = clazz.getDeclaredMethod("say", new Class[]{String.class});

        method.setAccessible(true);

        String retu = (String) method.invoke(p1, new Object[]{"hello world"});

        System.out.println(retu);

    }

}

class Private {

    @SuppressWarnings("unused")
    private String say(String param) {
        return param;
    }

}
