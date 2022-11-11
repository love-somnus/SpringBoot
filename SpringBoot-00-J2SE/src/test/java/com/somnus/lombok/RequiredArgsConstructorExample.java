package com.somnus.lombok;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 *     @RequiredArgsConstructor会将类的每一个final字段或者non-null字段生成一个构造方法
 * </p>
 * @author kevin.liu
 * @title: RequiredArgsConstructorExample
 * @projectName SpringBoot
 * @description: TODO
 * @date 2022/6/24 16:05
 */
@RequiredArgsConstructor
public class RequiredArgsConstructorExample {

    private String username;

    private final String password;

    @NonNull
    private String sex;

    public static void main(String[] args) {
        System.out.println();
    }
}

/**
 * public class RequiredArgsConstructorExample {
 *     private String username;
 *     private final String password;
 *     @NonNull
 *     private String sex;
 *
 *     public static void main(String[] args) {
 *         System.out.println();
 *     }
 *
 *     public RequiredArgsConstructorExample(final String password, @NonNull final String sex) {
 *         if (sex == null) {
 *             throw new NullPointerException("sex is marked non-null but is null");
 *         } else {
 *             this.password = password;
 *             this.sex = sex;
 *         }
 *     }
 * }
 */