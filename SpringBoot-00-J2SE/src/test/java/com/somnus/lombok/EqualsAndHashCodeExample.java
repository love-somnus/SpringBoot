/**
 * <p>Title: EqualsAndHashCodeExample.java </p>
 * <p>Description: TODO(用一句话描述该文件做什么) </p>
 *
 * @author pc
 * @version 1.0.0
 * @date 2018年9月21日
 */
package com.somnus.lombok;

import lombok.EqualsAndHashCode;

/**
 * @author pc
 * @ClassName: EqualsAndHashCodeExample
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2018年9月21日
 */
@EqualsAndHashCode
public class EqualsAndHashCodeExample {
    private String name;
    private double score;

    @EqualsAndHashCode.Exclude
    private Square square = new Square(5, 10);

    @EqualsAndHashCode.Exclude
    private int id;

    public static void main(String[] args) {
        System.out.println(new EqualsAndHashCodeExample());
        System.out.println(new EqualsAndHashCodeExample());
    }

    @EqualsAndHashCode
    public static class Square {
        private final int width, height;

        public Square(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
}
