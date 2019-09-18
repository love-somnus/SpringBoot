package com.somnus.inherit;

public class Inherit4 {
    public static void main(String[] args) {
        System.out.println(Parent.pi);
        System.out.println(Children.pi);

        System.out.println(Parent.retrun());
        System.out.println(Children.retrun());
    }

    static class Parent {
        public static final double pi = Math.PI;

        public static double retrun() {
            return Math.E;
        }

        @Override
        public String toString() {
            return "Parent";
        }
    }

    static class Children extends Parent {

        @Override
        public String toString() {
            return "Children";
        }
    }
}