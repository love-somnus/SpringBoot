package com.somnus.apache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.functors.UniquePredicate;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author Somnus
 * @version V1.0
 * @Title: CommonsCollections.java
 * @Package com.somnus.apache
 * @Description: TODO
 * @date 2015年6月12日 下午5:38:59
 */
public class CommonsCollectionTest {

    @Test
    public void collections(){
        System.out.println(Collections.singletonList(1));
        System.out.println(Collections.singleton(1));
        System.out.println(Collections.singletonMap("a", "1"));

        System.out.println(Collections.min(List.of(1, 2, 3)));
        System.out.println(Collections.max(List.of(4, 8, 3, 9, 5), Integer::compareTo));

        System.out.println(Collections.emptyList());
        System.out.println(Collections.emptySet());
        System.out.println(Collections.emptyMap());

        System.out.println(Collections.emptySortedSet());
        System.out.println(Collections.emptySortedMap());

        System.out.println(Collections.unmodifiableList(Arrays.asList("1", "2", "3")));
        System.out.println(Collections.unmodifiableSet(new HashSet<>(Arrays.asList("1", "2", "3"))));
        System.out.println(Collections.unmodifiableMap(ImmutableMap.of("a", 1)));
    }

    @Test
    public void collectionUtils() {

		/*集合判断： 
		例1: 判断集合是否为空:*/
        System.out.println(CollectionUtils.isEmpty(Collections.emptyList()));
        System.out.println(CollectionUtils.isEmpty(Arrays.asList("1", "2", "3")));

        /*例2: 判断集合是否不为空:*/
        System.out.println(CollectionUtils.isNotEmpty(Collections.emptySet()));
        System.out.println(CollectionUtils.isNotEmpty(Arrays.asList("1", "2", "3")));

        System.out.println(CollectionUtils.union(
                Arrays.asList("1", "2", "3"),
                Arrays.asList("2", "3", "5")));//并集{3, 2, 1, 5}

        System.out.println(CollectionUtils.intersection(
                Arrays.asList("1", "2", "3"),
                Arrays.asList("2", "3", "5")));//交集{3, 2}

        System.out.println(CollectionUtils.disjunction(
                Arrays.asList("1", "2", "3"),
                Arrays.asList("2", "3", "5")));//交集的补集{1, 5}

        System.out.println(CollectionUtils.subtract(
                Arrays.asList("2", "3", "5"),
                Arrays.asList("1", "2", "3")));//list1与list2的差{1}

        System.out.println(CollectionUtils.select(Arrays.asList("2", "3", "5", "5"), new UniquePredicate<>()));
        System.out.println(CollectionUtils.select(Arrays.asList("2", "3", "5", ""), (object) -> StringUtils.isNotEmpty(object)));
        System.out.println(CollectionUtils.select(Arrays.asList(2, 3, 5, 6), (object) -> object.intValue() <= 5));
    }

    public <E> List<E> unique(List<E> param, final String property) {
        final Set<Object> iSet = new HashSet<>();
        List<E> result = (List<E>) CollectionUtils.select(param, (object) -> {
            Object val = null;
            try {
                val = PropertyUtils.getProperty(object, property);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return iSet.add(val);
        });
        return result;
    }

    public static void main(String[] args) {
        List<Person> param = new ArrayList<>();
        param.add(new Person("admin", "password", new Date(), null, null));
        param.add(new Person("admin", "password2", new Date(), null, null));
        param.add(new Person("admin3", "password3", new Date(), null, null));
        param.add(new Person("admin", "password4", new Date(), null, null));

        CommonsCollectionTest util = new CommonsCollectionTest();
        System.out.println(util.unique(param, "username"));
    }

    @Test
    public void MapUtils() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", "dddd");
        map.put("b", 100);
        System.out.println(MapUtils.getString(map, "a"));
        System.out.println(MapUtils.getIntValue(map, "b"));
        System.out.println(MapUtils.isNotEmpty(map));
    }

    @Test
    public void ListUtils() {
        System.out.println(ListUtils.sum(Arrays.asList("1", "2", "3"), Arrays.asList("2", "3", "5")));
    }

    @Test
    public void SetUtils() {
        System.out.println(SetUtils.emptySortedSet());
    }

}
