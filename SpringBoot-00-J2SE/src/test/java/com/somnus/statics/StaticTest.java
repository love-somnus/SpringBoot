package com.somnus.statics;

/**
 * 执行顺序
 *
 * @author Somnus
 * @version V1.0
 * @Title: StaticTest.java
 * @Package com.somnus.accessModifier
 * @Description: TODO
 * @date 2015年6月11日 下午12:47:12
 */
public class StaticTest {
    static int arr[] = new int[5];
    public static void main(String  args[ ]){
        int  i,j,a[ ]={ 5,9,6,8,7};
        for(i=0;i<a.length-1;i++){
            int  k = i;
            for(j=i;j<a.length;j++)	   	if(a[j]<a[k])  k=j;
            int  temp=a[i];
            a[i]=a[k];	a[k]=temp;	}
        for(i =0;i<a.length; i++)
            System.out.print(a[i]+"  ");
        System.out.println( );
    }


}

class Animal {
    static {
        System.out.println("as");
    }

    public Animal() {
        System.out.println("ac");
    }
}

class Dog extends Animal {
    Cat cat = new Cat();

    static {
        System.out.println("ds");
    }

    public Dog() {
        System.out.println("dc");
    }
}

class Cat {
    public Cat() {
        System.out.println("cc");
    }
}
/* 1、在new B一个实例时首先要进行类的装载。（类只有在使用New调用创建的时候才会被java类装载器装入）
 * 2、在装载类时，先装载父类A，再装载子类B
 * 3、装载父类A后，完成静态动作（包括静态代码和变量，它们的级别是相同的，安装代码中出现的顺序初始化）
 * 4、装载子类B后，完成静态动作
 * 类装载完成，开始进行实例化
 * 1、在实例化子类B时，先要实例化父类A
 * 2、实例化父类A时，先成员实例化（非静态代码）
 * 3、父类A的构造方法
 * 4、子类B的成员实例化（非静态代码）
 * 5、子类B的构造方法
 */
