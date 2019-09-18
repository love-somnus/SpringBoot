package com.somnus.reference;

import java.lang.ref.WeakReference;

/**
 * @author Somnus
 * @brief   继承WeakReference，将Apple作为弱引用
 * @details 注意到时候回收的是Apple，而不是Salad
 * @date 2019/1/4 10:48
 */
public class Salad extends WeakReference<Apple> {
    public Salad(Apple apple) {
        super(apple);
    }
}