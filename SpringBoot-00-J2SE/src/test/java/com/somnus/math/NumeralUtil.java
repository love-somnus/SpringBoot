package com.somnus.math;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.junit.Test;

public class NumeralUtil {

    /*
     * 0.47以百分数输出
     */
    @Test
    public void test1() {
        double a = 1; double b = 3;
        long c = 1; Long cc = 1L; long d = 3; Long dd = 3L;
        NumberFormat nf = NumberFormat.getPercentInstance();
        //设置数值的【小数部分】允许的最大位数
        nf.setMaximumFractionDigits(1);
        //默认RoundingMode.HALF_UP 四舍五入。此方法只影响格式化，与解析无关
        nf.setRoundingMode(RoundingMode.HALF_UP);
        System.out.println(nf.format(0.4756));
        System.out.println(nf.format(8192.0/41863));
        System.out.println(nf.format(c/d));
        System.out.println(nf.format(cc.doubleValue()/dd.doubleValue()));
        System.out.println(nf.format(a/d));
        System.out.println(nf.format(1.0/3));
        System.out.println(nf.format(1/3.0));
        System.out.println(nf.format(1.0/3.0));
    }

    //#表示没有则为空，0表示如果没有则该位补0
    @Test
    public void test2() {
        double pi = Math.PI;
        System.out.println(pi);
        System.out.println("取一位整数:" + new DecimalFormat("00").format(pi));
        System.out.println("取一位整数和两位小数:" + new DecimalFormat("0.00").format(pi));
        System.out.println("取两位整数和三位小数，整数不足部分以0填补:" + new DecimalFormat("00.000").format(pi));

    }

    @Test
    public void test3() {
        double pi = 23045.69999999999999751;
        System.out.println(new DecimalFormat("0.00").format(7 * 0.3333));
        System.out.println(new DecimalFormat("0.00").format(7 * 0.6666));
        System.out.println("取所有整数部分:" + new DecimalFormat("#").format(pi));
        System.out.println("取一位整数和两位小数:" + new DecimalFormat("0.00").format(pi));
        System.out.println(new DecimalFormat("##.###").format(pi));

    }

}
