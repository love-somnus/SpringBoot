package com.somnus.inherit;

public class Inherit {

    public static void main(String[] args) {
        Children children = new Children();
        System.out.println(children.getCause());
    }

    static class Parent {
        private Parent cause = this;

        public Parent() {
            super();
            System.out.println("p>" + this);
        }

        public Parent getCause() {
            return cause;
        }

        @Override
        public String toString() {
            return "Parent";
        }
    }

    static class Children extends Parent {
        public Children() {
            super();
            System.out.println("c>" + this);
        }

        @Override
        public String toString() {
            return "Children";
        }
    }
}
