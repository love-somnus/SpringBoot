package com.somnus.java8.lambda;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author kevin.liu
 * @date 2022/9/2 21:28
 */
public class Java8Collection {

    @Test
    public void map2String(){
        Multimap<String, Integer> params = ImmutableMultimap.of("a", 1, "b", 2, "c", 3, "a", 2);

        String strr = params.entries().stream().map(p -> p.getKey() + "=" + p.getValue()).reduce((p1, p2) -> p1 + "&" + p2).map(s -> "?" + s).orElse("");
        System.out.println(strr);

        Map<String, String> map = Map.of("d", "1", "f", "2", "a", "3", "sign", "4");

        String str = map.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).reduce((p1, p2) -> p1 + "&" + p2).map(s -> "?" + s).orElse("");
        System.out.println(str);

        String str2 = map.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).sorted().collect(Collectors.joining("&"));
        System.out.println(str2);
    }

    @Test
    public void map2Optional(){
        Map<String, Fruit> fruits = Map.of("a", new Fruit("apple", 35), "b", new Fruit("banana", 56), "c", new Fruit("cherry", 2));
        Optional<Fruit> optional = fruits.values().stream().filter(v -> v.getName().equals("apple")).max(Comparator.comparingInt(Fruit::getNum));
        System.out.println(optional);
    }

    @Test
    public void string2Map(){
        String params = "d=1&sign=4&f=2&a=3";
        Map<String, String> map = Arrays.stream(params.split("&")).map(str -> str.split("=")).collect(Collectors.toMap(value -> value[0], value -> value[1], (u, v) -> v, LinkedHashMap::new));
        System.out.println(map);
    }

    @Test
    public void list2array(){
        List<String> nums = List.of("1", "2", "3", "4");
        //List<String> 转 Integer[ ]
        System.out.println(Arrays.toString(nums.stream().map(Integer::parseInt).toArray()));
        System.out.println(Arrays.toString(nums.stream().map(Long::parseLong).toArray()));
        System.out.println(Arrays.toString(nums.stream().map(Double::parseDouble).toArray()));
        System.out.println(Arrays.toString(nums.stream().map(Integer::parseInt).toArray(Integer[]::new)));
    }

    @Test
    public void array2list(){

        /* String[ ] 转 List<String> */
        List<String> fruits = Stream.of("apple", "banana").collect(Collectors.toList());
        System.out.println(fruits);
        List<String> flowers = Stream.of(new String[]{"rose", "tulip"}).collect(Collectors.toList());
        System.out.println(flowers);

        //String[ ] 转 Integer [ ]
        String[] strArray = new String[]{"5", "6", "1", "4", "9"};
        Integer[] integerArray = Arrays.stream(strArray).map(Integer::parseInt).toArray(Integer[]::new);
        System.out.println(Arrays.toString(integerArray));

        //int [ ] 转 List<Integer>
        int[] intArray = new int[]{5,6,1,4,9};
        List<Integer> integerList = Arrays.stream(intArray).boxed().collect(Collectors.toList());
        System.out.println(integerList);

        //int [ ] 转 Integer [ ]
        Integer[] integerArray2 = Arrays.stream(intArray).boxed().toArray(Integer[]::new);
        System.out.println(Arrays.toString(integerArray2));

        /* Arrays.asList(strArray)返回值是仍然是一个可变的集合，但是返回值是其内部类，不具有add方法，可以通过set方法进行增加值，默认长度是10 */
        System.out.println(Arrays.asList("apple", "banana"));
        /* 返回的是不可变的集合，但是这个长度的集合只有1，可以减少内存空间。但是返回的值依然是Collections的内部实现类，同样没有add的方法，调用add，set方法会报错*/
        System.out.println(Collections.singletonList("rose"));
    }

    @Test
    public void set2array(){
        Set<String> set = Set.of("1", "2", "3", "4");
        String[] array = set.toArray(new String[0]);
        System.out.println(Arrays.toString(array));
        String[] array2 = set.stream().toArray(String[]::new);
        System.out.println(Arrays.toString(array2));
    }
    @Test
    public void array2set(){
        String[] array = new String[]{"5", "6", "1", "4", "9"};
        Set<String> set = new HashSet<>(Arrays.asList(array));
        System.out.println(set);
        Set<String> set2 = Stream.of(array).collect(Collectors.toSet());
        System.out.println(set2);
    }

    @Test
    public void set2list(){
        Set<String> set = Set.of("1", "2", "3", "4");
        List<String> list = new ArrayList<>(set);
        System.out.println(list);
        List<String> list2 = Stream.of(set.toArray(new String[0])).collect(Collectors.toList());
        System.out.println(list2);
    }
    @Test
    public void list2set(){
        List<String> list = List.of("1", "2", "3", "4");
        Set<String> set = new HashSet<>(list);
        System.out.println(set);
        Set<String> set2 = list.stream().collect(Collectors.toSet());
        System.out.println(set2);
    }

    @Test
    public void list2Map(){
        List<Fruit> fruits = List.of(new Fruit("a", "apple"),new Fruit("b", "banana"),new Fruit("c", "cherry"));
        Map<String, Fruit> fruitMap = fruits.stream().collect(Collectors.toMap(Fruit::getAlias, Function.identity()));
        System.out.println(fruitMap);
        List<String> letters = List.of("a","w","d","f","c");
        List<String> f = letters.stream().filter(v -> fruitMap.get(v) != null).map(v -> fruitMap.get(v).getName()).collect(Collectors.toList());
        System.out.println(f);
    }

    @Test
    public void list2Map2(){
        //合并List<Map<String,Object>>为一个Map
        List<Map<String,Integer>> list = List.of(Map.of("a", 1, "b", 2), Map.of("c", 1, "d", 2), Map.of("a", 3, "d", 4));
        // 覆盖key相同的值，
        Map<String,Object> map = list.stream().map(Map::entrySet).flatMap(Set::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existing, replacement) -> replacement));
        System.out.println(map);

        // 覆盖key相同的值，
        Map<String,Object> map2 = list.stream().flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existing, replacement) -> replacement));
        System.out.println(map2);

        // 覆盖key相同的值，
        Map<String,Object> map3 = list.stream().map(Map::entrySet).flatMap(Collection::stream)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existing, replacement) -> replacement));
        System.out.println(map3);
    }

    @Test
    public void list2list(){
        List<Integer> l1 = List.of(3, 2);
        List<Integer> l2 = List.of(4, 5, 3);
        //两个List合并为一个新的List
        List<Integer> resultDouble = Stream.of(l1, l2).flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(resultDouble);
        //交集
        List<Integer> beMixed = l1.stream().filter(l2::contains).collect(Collectors.toList());
        System.out.println("交集:" + beMixed);//[3]
        //差集
        List<Integer> subtraction = l1.stream().filter(v -> !l2.contains(v)).collect(Collectors.toList());
        System.out.println("差集:" + subtraction);//[2]
    }

    @Test
    public void nest2list(){
        List<List<Integer>> nums = Arrays.asList(List.of(3, 2), List.of(4, 5, 3));
        List<Integer> result = nums.stream().filter(Objects::nonNull).reduce(new ArrayList<>(), (all, item ) -> {all.addAll(item); return all;});
        System.out.println(result);

        List<Integer> result2 = nums.stream().filter(Objects::nonNull).flatMap(Collection::stream)/*.distinct()*/
                .collect(Collectors.toList());
        System.out.println(result2);

        Integer[] result3 = nums.stream().filter(Objects::nonNull).flatMap(Collection::stream)/*.distinct()*/
                .toArray(Integer[]::new);
        System.out.println(Arrays.toString(result3));

        List<List<List<Integer>>> list = List.of(List.of(List.of(3, 2), List.of(4, 5, 3)), List.of(List.of(3, 2), List.of(4, 5, 3)));
        List<Integer> result4 = list.stream().filter(Objects::nonNull).flatMap(Collection::stream).flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(result4);
    }

    @Test
    public void arraylist2list(){
        List<String[]> arrayList = List.of(new String[]{"10111011001", "10119910001"},  new String[]{"11111017501", "10119910001"});
        List<String> list1 = arrayList.stream().filter(Objects::nonNull).flatMap(Arrays::stream)/*.distinct()*/
                .collect(Collectors.toList());
        System.out.println(list1);
        String[] array = arrayList.stream().filter(Objects::nonNull).flatMap(Arrays::stream)/*.distinct()*/
                .toArray(String[]::new);
        System.out.println(Arrays.toString(array));
    }

    @Test
    public void string2list(){
        String params = "apple=1111&banana=2222&pear=3333&grape=44444";
        List<String> list = Arrays.stream(params.split("&")).map(v -> v.split("=")).flatMap(Stream::of).collect(Collectors.toList());
        System.out.println(list);
    }

    @Test
    public void doubleArray2list(){
        String[] a = {"a", "b", "c"};
        String[] b = {"1", "2", "3"};
        //两个字符串数组合并为一个新的数组
        String[] c = Stream.of(a, b).flatMap(Stream::of).toArray(String[]::new);
        System.out.println(Arrays.toString(c));

        //两个String数组转List<String>
        List<String> strList = Stream.of(a, b).flatMap(Stream::of).collect(Collectors.toList());
        System.out.println(strList);

        int[] ai = new int[]{1,3};
        int[] bi = new int[]{2,4};
        //两个 int 型数组合并为一个新的 int 型数组
        int[] ci =  IntStream.concat(Arrays.stream(ai), Arrays.stream(bi)).toArray();
        System.out.println(Arrays.toString(ci));

        //两个int数组转List<Integer>
        List<Integer> integerList = Stream.of(IntStream.of(ai).boxed(), IntStream.of(bi).boxed()).flatMap(s -> s).collect(Collectors.toList());
        System.out.println(integerList);
    }

    @Test
    public void flatMap(){
        List<String> fruits = List.of("aa|111", "bb|222", "cc|333", "dd|111");

        List<String> list = fruits.stream().filter(Objects::nonNull).map(fruit -> fruit.split("[|]")).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
        System.out.println(list);

        List<String> list2 = fruits.stream().filter(Objects::nonNull).flatMap(v -> Arrays.stream(v.split("[|]"))).distinct().collect(Collectors.toList());
        System.out.println(list2);

        //"-分割后获取字母，转为新数组
        String[] strArray = fruits.stream().map(v -> v.split("[|]")[0]).toArray(String[]::new);
        //"-分割后获取字母，转List
        List<String> lists = fruits.stream().map(v -> v.split("[|]")[0]).collect(Collectors.toList());
        //"-分割后获取字母，用逗号拼接为字符串
        String str = fruits.stream().map(v -> v.split("[|]")[0]).collect(Collectors.joining(","));

        System.out.println(Arrays.toString(strArray));//[aa, bb, cc, dd]
        System.out.println(lists);//[aa, bb, cc, dd]
        System.out.println(str);//aa,bb,cc,dd
    }
}
