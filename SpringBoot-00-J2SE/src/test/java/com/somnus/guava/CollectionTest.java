package com.somnus.guava;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.*;
import com.google.common.primitives.Ints;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @ClassName: IterablesTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Somnus
 * @date 2018年9月27日
 */
public class CollectionTest {

    @Test
    public void Iterables() {
        Iterable<Integer> concatenated = Iterables.concat(
                Ints.asList(1, 2, 3),
                Ints.asList(4, 5, 6));
        concatenated.forEach(System.out::println);

        System.out.println(Iterables.getLast(concatenated));
        System.out.println(Iterables.getFirst(concatenated,99));
    }

    @Test
    public void Lists() {
        List<String> list = Lists.newArrayList();
        list.forEach(System.out::println);

        List<String> list2 = Lists.newArrayList("a","b","c");
        list2.forEach(System.out::println);

        List<Integer> countUp = Ints.asList(1, 2, 3, 4, 5);
        countUp.forEach(System.out::println);

        List<String> list3 = ImmutableList.<String>builder().add("red", "green","black","white","grey").build();
        List<String> list4 = ImmutableList.of("red", "green","black","white","grey");
        list3.forEach(System.out::println);
        list4.forEach(System.out::println);

        List<Integer> countDown = Lists.reverse(countUp); // {5, 4, 3, 2, 1}
        countDown.forEach(System.out::println);

        List<List<Integer>> parts = Lists.partition(countUp, 2); // {{1, 2}, {3, 4}, {5}}
        parts.forEach(System.out::println);
    }

    @Test
    public void Sets() {
        Set<String> set1st = Sets.newHashSet();
        Set<String> set2nd = Sets.newLinkedHashSet();
        Set<String> set3rd = Sets.newTreeSet();
        set1st.forEach(System.out::println);
        set2nd.forEach(System.out::println);
        set3rd.forEach(System.out::println);

        Set<String> primes = ImmutableSet.of("two", "three", "five", "seven");
        primes.forEach(System.out::println);
        Set<String> primes2 = ImmutableSet.<String>builder().add("red", "green","black","white","grey").build();
        primes2.forEach(System.out::println);

        //集合操作
        Set<Integer> set1=Sets.newHashSet(1,2,3,4,5,6);
        Set<Integer> set2=Sets.newHashSet(4,5,6,7,8,9);
        Set<Integer> intersectionSet=Sets.intersection(set1, set2);
        Set<Integer> differenceSet=Sets.difference(set1, set2);
        Set<Integer> unionSet=Sets.union(set1, set2);
        intersectionSet.forEach(System.out::println);
        differenceSet.forEach(System.out::println);
        unionSet.forEach(System.out::println);
    }

    @Test
    public void Maps() {
        Map<String, Integer> map = Maps.newHashMap();
        map.forEach((k,v) -> {
            System.out.println(k);
        });

        Map<String, Integer> left = ImmutableMap.of("a", 1, "b", 2, "c", 3);
        Map<String, Integer> right = ImmutableMap.of("b", 2, "c", 4, "d", 5);
        MapDifference<String, Integer> diff = Maps.difference(left, right);

        diff.entriesInCommon(); // {"b" => 2}
        diff.entriesDiffering(); // {"c" => (3, 4)}
        diff.entriesOnlyOnLeft(); // {"a" => 1}
        diff.entriesOnlyOnRight(); // {"d" => 5}

        //超过5个用这个
        Map<String, String> im = ImmutableMap.<String, String>builder()
                .put("a", "1")
                .put("b", "2")
                .put("c", "3")
                .put("d", "4")
                .put("e", "5")
                .put("f", "6")
                .build();
        im.forEach((k,v) -> {
            System.out.println(k);
        });

        //guaava 相同key用这个
        Multimap<String, Integer> m = ImmutableMultimap.of("a", 1, "b", 2, "c", 3, "a", 11);
        System.out.println(m.toString());
        Multimap<String, String> imap = ImmutableMultimap.<String, String>builder()
                .put("a", "1")
                .put("b", "2")
                .put("c", "3")
                .put("d", "4")
                .put("e", "5")
                .put("a", "11")
                .build();
        System.out.println(imap.toString());
        imap.entries().forEach(entry -> System.out.println(entry.getKey() + ":" + entry.getValue()));

        //spring
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("a", "1");
        params.add("b", "2");
        params.add("c", "3");
        params.add("a", "11");
        params.forEach((k,v) -> {
            System.out.println(k + ":" + v);
        });
        params.forEach((key, value) -> System.out.println(key + ":" + value));
        params.values().stream().flatMap(List::stream).forEach(System.out::println);
    }
}
