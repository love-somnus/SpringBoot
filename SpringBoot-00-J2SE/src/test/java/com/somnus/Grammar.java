package com.somnus;

import java.util.Random;

import org.junit.Test;

/**
 * @author Somnus
 * @version V1.0
 * @Title: Grammar.java
 * @Package com.somnus
 * @Description: TODO
 * @date 2015年6月8日 上午11:36:43
 */
public class Grammar {
    /*
     * 0%2=0 1%2=1[2%3=2] 2%2=0
     * 0/2=0 1/2=0[2/3=0] 2/2=1
     */
    public static void main(String[] args) {
        System.out.println(2 % 3);
        System.out.println(2 / 3);
        double num1 = 0.1d, num2 = 0.1;
        float f1 = 0f;
        //小数默认都是double型
        //精度double 大于 float ，所以不能double向下转型 float ，但float可以向上转型double
        System.out.println(num1 + num2);
        System.out.println(0.05 + 0.01);
        System.out.println(0.05 + 0.01);
        System.out.println(1.0 - 0.42);
        System.out.println(num1 == num2);
        System.out.println(num1 + "|" + num2 + "|" + f1);
    }

    /**
     * 只能在循环体内和switch语句体内使用break语句。
     * 当break出现在循环体中的switch语句体内时，其作用只是跳出该switch语句体。
     * 当break出现在循环体中，但并不在switch语句体内时，则在执行break后，跳出本层循环体。
     * 在循环结构中，应用break语句使流程跳出本层循环体，从而提前结束本层循环
     */
    @Test
    public void testBreak() {
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                System.out.println("i=" + i);
            } else {
                System.out.println("执行了break语句,跳出当前循环!");
                break;
            }
        }
    }

    /**
     * 其作用是结束本次循环，即跳过本次循环体中余下尚未执行的语句，接着再一次进行循环的条件判定。
     * 注意：执行continue语句并没有使整个循环终止。在while和do-while循环中，continue语句使得流程直接跳到循环控制条件的测试部分 ，然后决定循环是否继续进行。
     * 在for 循环中，遇到continue后，跳过循环体中余下的语句，而去对for语句中的“表达式3”求值，然后进行“表达式2”的条件测试，  最后根据“表达式2”的值来决定for循环是否执行。
     */
    @Test
    public void testContinue() {
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                System.out.println("没有执行continue语句输出i=" + i);
            } else {
                System.out.println("执行了Continue语句,跳出当前循环!i=" + i);
                continue;
            }
            System.out.println("*******如果i%2==0，则这句话会被显示i=" + i);
        }
    }

    @Test
    public void testReturn() {
        for (int i = 0; i < 10; i++) {
            if (i == 1) {
                System.out.println(i);
                System.out.println("执行了return语句,直接跳出了当前的testReturn方法!");
                return;
            } else {
                System.out.println("等于0时会执行到===" + i);
            }
        }
        System.out.println("本语句永远不会有机会执行到");
    }

    @Test
    public void testReturn2() {
        for (int i = 0; i < 3; i++) {
            body(i);
        }
        System.out.println("本语句永远不会有机会执行到");
    }

    public static void body(int i) {
        if (i == 2) {
            System.out.println("return" + i + "返回,但并不影响调用者的执行");
            return;
        }
        System.out.println("body" + i + "执行结束");
    }

    @Test
    public void testWhile() {
        boolean flag = true;
        while (flag) {
            System.out.println("while循环。。。。。。");
            if (new Random().nextInt(5) == 2) {
                flag = false;
            }
        }
        System.out.println("最后执行");
    }

    @Test
    public void testWhile2() {
        while (true) {
            System.out.println("while循环。。。。。。");
            if (new Random().nextInt(5) == 2) {
                break;
            }
        }
        System.out.println("最后执行");
    }
}
