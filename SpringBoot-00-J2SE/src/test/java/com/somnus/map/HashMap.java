package com.somnus.map;

import java.util.UUID;

public class HashMap {

    public static void main(String[] args) {

        java.util.HashMap<String, String> map = new java.util.HashMap<String, String>();

        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    map.put(UUID.randomUUID().toString(), "");
                }
                System.out.println(Thread.currentThread().getName() + " put over");
            }).start();
        }

    }

}
