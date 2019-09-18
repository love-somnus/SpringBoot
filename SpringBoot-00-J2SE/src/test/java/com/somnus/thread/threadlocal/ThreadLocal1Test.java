package com.somnus.thread.threadlocal;

/**
 * @author Somnus
 * @version V1.0
 * @Title: ThreadLocalTest.java
 * @Package com.somnus.thread.threadlocal
 * @Description: TODO
 * @date 2015年4月25日 上午9:54:27
 */
public class ThreadLocal1Test {
    // 创建一个Integer型的线程本地变量
    public static final ThreadLocal<Integer> container = ThreadLocal.withInitial(() -> 0);

    public void execute() {
        for (int j = 0; j < 10; j++) {
            new Thread(() -> {
                // 获取当前线程的本地变量，然后累加1000次
                int num = container.get();
                for (int i = 0; i < 1000; i++) {
                    num++;
                }
                // 重新设置累加后的本地变量
                container.set(num);
                System.out.println(Thread.currentThread().getName() + " : " + container.get());
                container.remove();
            }, "Thread-" + j).start();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadLocal1Test test = new ThreadLocal1Test();
        test.execute();
    }
}