package com.somnus.map;

import java.util.UUID;

public class ConcurrentHashMap {

    public static void main(String[] args) {

        java.util.concurrent.ConcurrentHashMap<String, String> map = new java.util.concurrent.ConcurrentHashMap<String, String>();

        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    map.put(UUID.randomUUID().toString(), "");
                }
                System.out.println(Thread.currentThread().getName() + " put over");
            }).start();
        }

    }
}
