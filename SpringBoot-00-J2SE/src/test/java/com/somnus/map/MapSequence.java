package com.somnus.map;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Hashtable.keySet()           降序
 * TreeMap.keySet()             升序
 * HashMap.keySet()             乱序
 * LinkedHashMap.keySet()       原序
 */
public class MapSequence {
    public static void main(String[] args) {
        /////////////////////////////////////////////////////////////
        // TEST
        /////////////////////////////////////////////////////////////
        System.out.println("##  Hashtable  ##");
        Hashtable<String, String> ht = new Hashtable<String, String>();
        ht.put("2017-08", "OOO");
        ht.put("2017-07", "OOO");
        ht.put("2017-11", "OOO");
        ht.put("2017-12", "OOO");
        ht.put("2017-06", "OOO");
        System.out.println(ht.get("aaa"));
        for (Iterator<String> it = ht.keySet().iterator(); it.hasNext(); ) {
            System.out.println(it.next());
        }

        /////////////////////////////////////////////////////////////
        // TEST
        /////////////////////////////////////////////////////////////
        System.out.println("##  TreeMap  ##");
        TreeMap<String, String> tm = new TreeMap<String, String>();
        tm.put("2017-08", "OOO");
        tm.put("2017-07", "OOO");
        tm.put("2017-11", "OOO");
        tm.put("2017-12", "OOO");
        tm.put("2017-06", "OOO");
        for (Iterator<String> it2 = tm.keySet().iterator(); it2.hasNext(); ) {
            System.out.println(it2.next());
        }

        /////////////////////////////////////////////////////////////
        // TEST
        /////////////////////////////////////////////////////////////
        System.out.println("##  HashMap  ##");
        Map<String, String> hm = new HashMap<String, String>();
        hm.put("2017-08", "OOO");
        hm.put("2017-07", "OOO");
        hm.put("2017-11", "OOO");
        hm.put("2017-12", "OOO");
        hm.put("2017-06", "OOO");
        for (Iterator<String> it3 = hm.keySet().iterator(); it3.hasNext(); ) {
            System.out.println(it3.next());
        }

        /////////////////////////////////////////////////////////////
        // TEST
        /////////////////////////////////////////////////////////////
        System.out.println("##  LinkedHashMap  ##");
        LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();
        lhm.put("2017-08", "OOO");
        lhm.put("2017-07", "OOO");
        lhm.put("2017-11", "OOO");
        lhm.put("2017-12", "OOO");
        lhm.put("2017-06", "OOO");
        for (Iterator<String> it4 = lhm.keySet().iterator(); it4.hasNext(); ) {
            System.out.println(it4.next());
        }
    }

}
