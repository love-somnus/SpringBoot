package com.somnus.lombok;

import lombok.experimental.UtilityClass;

/**
 * @author kevin.liu
 * @title: UtilityClassExample
 * @projectName SpringBoot
 * @description: TODO
 * @date 2022/7/15 18:59
 */
@UtilityClass
public class UtilityClassExample {

    public Integer add(Integer a, Integer b){
        return a + b;
    }
}
/**
 * public final class UtilityClassExample {
 *     public static Integer add(Integer a, Integer b) {
 *         return a + b;
 *     }
 *
 *     private UtilityClassExample() {
 *         throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
 *     }
 * }
 */
