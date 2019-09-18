package com.somnus.thread.lock;

public class SynObj {

    // 方法锁/或者对象锁
    public synchronized void methodA() {
        System.out.println("methodA.....");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void methodB() {
        // 对代码块进行锁，降低锁的竞争
        synchronized (this) {
            System.out.println("methodB.....");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void methodC() {
        String str = "sss";
        // 这里锁的是 str 这个对象，而不是 SynObj 对象
        synchronized (str) {
            System.out.println("methodC.....");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final SynObj obj = new SynObj();

        for (int i = 0; i < 2; i++) {
            new Thread(() -> obj.methodA()).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> obj.methodB()).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> obj.methodC()).start();
        }

    }
}