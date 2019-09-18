package com.somnus.thread.threadlocal;

import org.junit.Test;

public class ContextTest {

    @Test
    public void test() {
        for (int j = 0; j < 10; j++) {
            new Thread(() -> {
                System.out.println(ActionContext.getInstance());
                // 获取当前线程的本地变量，然后累加1000次
                Integer index = ActionContext.getInstance().getInteger();
                for (int i = 0; i < 1000; i++) {
                    index++;
                }
                // 重新设置累加后的本地变量
                ActionContext.getInstance().setInteger(index);
                System.out.println(Thread.currentThread().getName() + " : " + index);
                ActionContext.getInstance().remove();
            }, "Thread-" + j).start();
        }
    }

    @Test
    public void test2() {
        for (int j = 0; j < 10; j++) {
            new Thread(() -> {
                // 获取当前线程的本地变量，然后累加1000次
                Integer index = ActionContext2.getInteger();
                for (int i = 0; i < 1000; i++) {
                    index++;
                }
                // 重新设置累加后的本地变量
                ActionContext2.setInteger(index);
                System.out.println(Thread.currentThread().getName() + " : " + ActionContext2.getInteger());
                ActionContext2.remove();
            }, "Thread-" + j).start();
        }
    }
}
/* Struts2的ActionContext便是采用的此方案 */

/**
 * 以自身对象作为ThreadLocal绑定值，必须保证每个线程都生成一个自己的上下文对象，并且任何时候拿到的都是属于当前线程的，具体参照 getInstance方法
 **/
class ActionContext {
    private static ThreadLocal<ActionContext> actionContext = new ThreadLocal<ActionContext>();

    private int integer;

    public int getInteger() {
        return integer;
    }

    public void setInteger(int integer) {
        this.integer = integer;
    }

    private ActionContext() {
    }

    public static ActionContext getInstance() {
        ActionContext context = actionContext.get();
        if (context == null) {
            context = new ActionContext();
            actionContext.set(context);
        }
        return context;
    }

    public void remove() {
        actionContext.remove();
    }
}

/**
 * 以上下文中每个具体的静态属性作为ThreadLocal绑定值，每个线程任何时候拿到上下文类中的属性都是当前线程的
 **/
class ActionContext2 {

    private static ThreadLocal<Integer> container = ThreadLocal.withInitial(() -> 0);

    public static Integer getInteger() {
        return container.get();
    }

    public static void setInteger(Integer num) {
        container.set(num);
    }

    public static void remove() {
        container.remove();
    }
}