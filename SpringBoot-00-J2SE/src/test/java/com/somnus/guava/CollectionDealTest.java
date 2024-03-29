package com.somnus.guava;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2018/12/15 15:18
 */
public class CollectionDealTest {

    @Test
    public void transform(){
        List<Long> longtime = Longs.asList(100000000L,999999999999999L,200000000L);
        Collection<String> timeStrCol2 = Collections2.transform(longtime, (input) -> new SimpleDateFormat("yyyy-MM-dd").format(input));
        timeStrCol2.forEach(System.out::println);
    }

    @Test
    public void filter(){
        List<String> list = Lists.newArrayList("big", "mom", "hoh", "dad", "refer", "Viking");
        Collection<String> list2 = Collections2.filter(list, (input) -> new StringBuilder(input).reverse().toString().equals(input));
        list2.forEach(System.out::println);
    }

    @Test
    public void compose(){
        //确保容器内字符串的长度不超过5，超过得部分截断，然后全转换成大写。
        List<String> list=Lists.newArrayList("hello","happiness","Viking","iloveu");
        Collection<String> col = Collections2.transform(list,
                Functions.compose(
                        (input) -> input.length()>5 ? input.substring(0,5):input,
                        (input) -> input.toUpperCase()
                )
        );
        col.forEach(System.out::println);
    }
}
