/**
 * <p>Title: BuilderExample.java </p>
 * <p>Description: TODO(用一句话描述该文件做什么) </p>
 *
 * @author pc
 * @version 1.0.0
 * @date 2018年9月21日
 */
package com.somnus.lombok;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName: AccessorsExample
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author pc
 * @date 2018年9月21日
 */
@Data
@Accessors(fluent = true)
public class AccessorsExample {

    private String name;

    private int age;

    public static void main(String[] args) {
        AccessorsExample access = new AccessorsExample().name("somnus").age(12);
        System.out.println(access.age);
    }

}
