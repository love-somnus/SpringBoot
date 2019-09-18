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
 * @author pc
 * @date 2018年9月21日
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class BuilderExample {
    @Builder.Default
    private long created = System.currentTimeMillis();

    private String name;

    private int age;

    @Singular
    private List<Integer> years;

    @Singular
    private Set<String> occupations;

    public static void main(String[] args) {
        System.out.println(BuilderExample.builder().created);
        System.out.println(BuilderExample.builder().build().created);
        System.out.println(BuilderExample.builder().build().getCreated());
        //要获得此行为，需要使用注释字段/参数@Singular
        System.out.println(BuilderExample.builder().name("somnus").occupation("abc").occupation("def"));

        BuilderExample example = BuilderExample.builder().name("somnus").year(1999).year(2000).build();
        System.out.println(example);

        //修改实体，要求实体上添加@Builder(toBuilder=true)
        System.out.println(example.toBuilder().name("sm").build());
        System.out.println(example.toBuilder().age(11).build());

        example.setName("ms");
        System.out.println(example);
    }

}
