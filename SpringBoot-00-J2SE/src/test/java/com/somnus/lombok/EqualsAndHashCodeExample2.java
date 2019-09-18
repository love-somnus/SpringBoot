/**
 * <p>Title: EqualsAndHashCodeExample.java </p>
 * <p>Description: TODO(用一句话描述该文件做什么) </p>
 *
 * @author pc
 * @version 1.0.0
 * @date 2018年9月21日
 */
package com.somnus.lombok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author pc
 * @ClassName: EqualsAndHashCodeExample
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2018年9月21日
 */
public class EqualsAndHashCodeExample2 {

    public static void main(String[] args) {
        Dog dog1 = new Dog(1);
        dog1.setColor("red");
        dog1.setWeight(2.0);

        Dog dog2 = new Dog(1);
        dog2.setColor("blue");
        dog2.setWeight(1.0);
        /**
         * 子类加上 @EqualsAndHashCode(callSuper = true)  结果为false
         * 子类不加上 默认@EqualsAndHashCode(callSuper = false)  结果为true
         * 由于@data 实际上就是用的 @EqualsAndHashCode，没有调用父类的equals()，当我们需要比较父类属性时，是无法比较的
         */
        System.out.println(dog1.equals(dog2));
    }

}

@Data
class Animal{
    private double weight;

    private String color;
}
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
class Dog extends Animal{

    private int age;
}
