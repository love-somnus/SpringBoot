package com.somnus.uuid;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * @author Somnus
 * @version V1.0
 * @Title: Identities.java
 * @Package com.somnus.uuid
 * @Description: TODO
 * @date 2015年5月28日 下午5:17:11
 */
public class Identities {

    private static SecureRandom random = new SecureRandom();

    private static final String RANDOM_STRING_RANGE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String RANDOM_NUMBER_RANGE = "0123456789";

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割
     * (128Bits)
     */
    public static String get32LenUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割
     */
    public static String get36LenUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    /**
     * 生成指定位数的随机字符串
     *
     * @param length
     * @param isNumber
     * @return
     */
    public static String randomString(int length, boolean isNumber) {
        StringBuilder builder = new StringBuilder(isNumber ? RANDOM_NUMBER_RANGE : RANDOM_STRING_RANGE);
        StringBuilder resultBuilder = new StringBuilder();
        Random r = new Random();
        int range = builder.length();
        for (int i = 0; i < length; i++) {
            resultBuilder.append(builder.charAt(r.nextInt(range)));
        }
        return resultBuilder.toString();
    }

}
