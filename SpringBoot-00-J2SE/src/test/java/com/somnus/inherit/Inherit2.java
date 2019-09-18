package com.somnus.inherit;

/**
 * 从【继承的概念】来说，private和final不被继承。Java官方文档上是这么说的。
 * 从内存的角度来说，父类的一切都被继承
 * (从父类构造方法被调用就知道了，因为new一个对象，就会调用构造方法， 子类被new的时候就会调用父类的构造方法，所以从内存的角度来说，子类拥有一个完整的父类)。
 * 子类对象所引用的内存有父类变量的一份拷贝。
 */
public class Inherit2 {
    public static void main(String[] args) {
        Children c = new Children(1, 2);
        System.out.println(c.getHeight());
        System.out.println(c.weight);

        Parent p = c;
        System.out.println(p.height);
        p.height = 3;

        System.out.println(p.height);
        System.out.println(c.getHeight());
    }

    static class Parent {
        private int height;

        public Parent() {
        }

        public Parent(int height) {
            this.height = height;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

    }

    static class Children extends Parent {
        public int weight;

        public Children() {
        }

        public Children(int height, int weight) {
            super(height);
            this.weight = weight;
        }
    }
}