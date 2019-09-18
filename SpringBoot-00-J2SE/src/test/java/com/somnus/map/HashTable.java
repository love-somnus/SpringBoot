package com.somnus.map;

import java.util.UUID;

public class HashTable {

    public static void main(String[] args) {

        java.util.Hashtable<String, String> map = new java.util.Hashtable<String, String>();

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
