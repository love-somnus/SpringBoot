package com.somnus;

import java.util.Scanner;

/**
 * @title: Demo
 * @description: TODO
 * @date 2020/4/10 13:49
 */
public class Demo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num,temp;
        do{
            System.out.print("请输入一个4位正整数：");
            num = scanner.nextInt();
        } while (num<1000||num>9999);
        int a[] = new int[4];
        a[0] = num/1000;
        a[1] = (num/100)%10;
        a[2] = (num/10)%10;
        a[3] = num%10;
        for(int j=0;j<4;j++){
            a[j]*=2;
            a[j]%=10;
        }
        for(int j=0;j<=1;j++) {
            temp = a[j];
            a[j] = a[2+j];
            a[2+j] =temp;
        }
        System.out.print("加密后的数字为：");
        for(int j=0;j<4;j++){
            System.out.print(a[j]);
        }
    }

}
