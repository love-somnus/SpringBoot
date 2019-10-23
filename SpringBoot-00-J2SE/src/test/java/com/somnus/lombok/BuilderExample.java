/**
 * <p>Title: BuilderExample.java </p>
 * <p>Description: TODO(用一句话描述该文件做什么) </p>
 *
 * @author pc
 * @version 1.0.0
 * @date 2018年9月21日
 */
package com.somnus.lombok;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import lombok.*;

/**
 * @ClassName: BuilderExample
 * @Description: @Singular只能应用于lombok已知的集合类型。目前，支持的类型是：
 *               java.util：
 *                  Iterable，Collection和List
 *                  Set，SortedSet和NavigableSet
 *                  Map，SortedMap和NavigableMap
 *              com.google.common.collect：
 *                  ImmutableCollection和ImmutableList
 *                  ImmutableSet和ImmutableSortedSet
 *                  ImmutableMap，ImmutableBiMap和ImmutableSortedMap
 *                  ImmutableTable
 * @author Somnus
 * @date 2018年9月21日
 */
@ToString
@Builder(toBuilder = true)
public class BuilderExample {
    @Builder.Default
    private long created = System.nanoTime();

    private String name;

    private int age;

    @Singular
    private List<Integer> years;

    @Singular
    private Set<String> occupations;

    public static void main(String[] args) {
        /** 使用了@Builder注解默认没有空构造函数，只有全参构造函数 */
        /*new BuilderExample();*/
        System.out.println(new BuilderExample(2019L, "admin", 30, Ints.asList(), Sets.newHashSet()));

        System.out.println(BuilderExample.builder().created);
        System.out.println(BuilderExample.builder().build().created);

        //要获得此行为，需要使用注释字段/参数@Singular
        System.out.println(BuilderExample.builder().name("somnus").occupation("abc").occupation("def").build());

        System.out.println("***************************************************************************************");

        BuilderExample example = BuilderExample.builder().name("somnus").year(1999).year(2000).build();
        System.out.println(example);

        System.out.println("**************************本质是产生了新的对象*****************************************");
        //修改实体，要求实体上添加@Builder(toBuilder=true)
        System.out.println(example.toBuilder().name("sm").build());
        System.out.println(example.toBuilder().age(11).build());

        System.out.println("**************************依然还是原来的那个对*****************************************");
        System.out.println(example);
    }

}
