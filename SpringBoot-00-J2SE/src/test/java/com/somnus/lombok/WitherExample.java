/**
 * <p>Title: BuilderExample.java </p>
 * <p>Description: TODO(用一句话描述该文件做什么) </p>
 *
 * @author pc
 * @version 1.0.0
 * @date 2018年9月21日
 */
package com.somnus.lombok;

import lombok.*;
import lombok.experimental.Wither;

import java.util.Set;

/**
 * @ClassName: BuilderExample
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author pc
 * @date 2018年9月21日
 */
@Wither
@AllArgsConstructor
@RequiredArgsConstructor
public class WitherExample {

    private long created = System.currentTimeMillis();

    @NonNull
    private String name;

    @NonNull
    private int age;

    public static void main(String[] args) {
        System.out.println(new WitherExample("admin", 12).withCreated(1111L).withAge(22).withName("somnus"));
    }

}
