package com.somnus;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;

public class BigDecimalTest {

    @Test
    public void dou() {
        System.out.println(0.06 + 0.01);
        System.out.println(1.0 - 0.42);
        System.out.println(4.015 * 100);
        System.out.println(303.1 / 1000);
    }

    @Test
    public void error() {
        BigDecimal a = new BigDecimal(2.23);
        BigDecimal b = new BigDecimal(1.93);
        System.out.println(a.add(b));
    }

    @Test
    public void add() {
        BigDecimal a = new BigDecimal("2.23");
        BigDecimal b = new BigDecimal("1.93");
        System.out.println(a.add(b));

        System.out.println(new BigDecimal(100L).add(new BigDecimal(500L)));
        System.out.println(new BigDecimal(100L).add(new BigDecimal(2.23)));
        System.out.println(new BigDecimal(100L).add(new BigDecimal("2.23")));
    }

    @Test
    public void subtract() {
        BigDecimal a = new BigDecimal("2.23");
        BigDecimal b = new BigDecimal("1.93");
        System.out.println(a.subtract(b));
    }

    @Test
    public void multiply() {
        BigDecimal a = new BigDecimal("2.23");
        BigDecimal b = new BigDecimal("100");
        System.out.println(a.multiply(b).setScale(0, RoundingMode.HALF_UP));
        System.out.println(a.multiply(b).setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void divide() {
        BigDecimal a = new BigDecimal("1.00");
        BigDecimal b = new BigDecimal("3.00");
        System.out.println(a.divide(b, 3, RoundingMode.UP));// 直接进位处理
        System.out.println(a.divide(b, 3, RoundingMode.DOWN));// 直接删除多余的小数位
        System.out.println(a.divide(b, 3, RoundingMode.HALF_UP));// 四舍五入 如果是5则向上舍
        System.out.println(a.divide(b, 3, RoundingMode.HALF_DOWN));// 四舍五入 如果是5则向下舍
    }

    public static void passByValue(BigDecimal v) {
        v = v.add(BigDecimal.TEN);
    }

    public static void main(String[] args) {
        System.out.println(BigDecimal.ZERO.equals(new BigDecimal("0")));
        System.out.println(BigDecimal.ZERO.equals(BigDecimal.ZERO));
        System.out.println(BigDecimal.ONE.equals(new BigDecimal("0")));
        System.out.println(BigDecimal.ONE.equals(BigDecimal.ZERO));

        BigDecimal p = BigDecimal.ONE;
        passByValue(p);
        System.out.println("1-->" + p);

        System.out.println("2-->" + BigDecimal.ZERO);
        System.out.println("3-->" + new BigDecimal(0));
        System.out.println("4-->" + new BigDecimal("0"));
        System.out.println("5-->" + new BigDecimal(0.0));
        System.out.println("6-->" + new BigDecimal("0.0"));

        System.out.println("7-->" + new BigDecimal(0.0).compareTo(new BigDecimal("0.0")));

        System.out.println("8-->" + new BigDecimal("0.10"));
        System.out.println("9-->" + new BigDecimal("0.10").doubleValue());

    }

}
