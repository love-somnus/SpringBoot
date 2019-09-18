package com.somnus.inherit;

/**
 * 从【继承的概念】来说，private和final不被继承。Java官方文档上是这么说的。
 * 从内存的角度来说，父类的一切都被继承
 * (从父类构造方法被调用就知道了，因为new一个对象，就会调用构造方法， 子类被new的时候就会调用父类的构造方法，所以从内存的角度来说，子类拥有一个完整的父类)。
 * 子类对象所引用的内存有父类变量的一份拷贝。
 */
public class Inherit3 {
    public static void main(String[] args) {
        Children c = new Children(160);
        System.out.println(c.getHeight());
    }

    static class Parent {
        private int height;

        public Parent(int height) {
            this.height = height;
            System.out.println("p>" + this);
        }

        public int getHeight() {
            return height;
        }

        @Override
        public String toString() {
            return "Parent" + this.getHeight();
        }
    }

    static class Children extends Parent {
        public Children(int height) {
            super(height);
            System.out.println("c>" + this);
        }

        @Override
        public String toString() {
            return "Children" + this.getHeight();
        }
    }
}