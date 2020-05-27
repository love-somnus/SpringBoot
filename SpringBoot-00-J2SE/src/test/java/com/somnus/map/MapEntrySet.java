package com.somnus.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

/**
 * 遍历MAP的两种方式
 *
 * @author Somnus
 * @version V1.0
 * @Title: MapEntrySet.java
 * @Package com.somnus.map
 * @Description: TODO
 * @date 2015年6月9日 下午5:06:47
 */
public class MapEntrySet {

    public static Map<String, String> map = new HashMap<String, String>();

    static {
        map.put("aa", "AAAA");
        map.put("bb", "BBBB");
        map.put("cc", "CCCC");
        map.put("dd", "DDDD");
    }

    /**
     * 如果只需要map的key或者values，用map的keySet或values方法无疑是最方便的
     */
    @Test
    public void test1() {
        for (String key : map.keySet()) {
            if("bb".equals(key)){
                map.put("bb","3445");
            }
            System.out.println(key);
        }
        for (String value : map.values()) {
            System.out.println(value);
        }
    }

    /**
     * 通过对map entrySet的遍历，也可以同时拿到key和value，一般情况下，性能上要优于上一种,这一种也是最常用的遍历方法。
     */
    @Test
    public void test2() {
        for (Entry<String, String> entry : map.entrySet()) {
            String key = (String) entry.getKey();

            String value = (String) entry.getValue();

            System.out.println(key + ":" + value);
        }
    }

    /**
     * 在用foreach遍历map时，如果改变其大小，会报错，但如果只是删除元素，可以使用Iterator的remove方法删除元素。
     */
    @Test
    public void test3() {
        for (Iterator<Entry<String, String>> it = map.entrySet().iterator(); it.hasNext(); ) {
            Entry<String, String> entry =  it.next();

            String key = entry.getKey();

            String value = entry.getValue();

            System.out.println(key + ":" + value);

            it.remove();
        }
        System.out.println(map);
    }

    @Test
    public void test4() {
        map.forEach((key, value) -> {
            System.out.println(key + ":" + value);
        });
    }


}
