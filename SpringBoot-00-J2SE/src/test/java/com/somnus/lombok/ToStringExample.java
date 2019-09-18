package com.somnus.lombok;

import lombok.ToString;

/**
 * @ClassName: ToStringExample
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author pc
 * @date 2018年9月21日
 */
@ToString
public class ToStringExample {
  private String name = "somnus";
  private Square square = new Square(5, 10);
  private String[] tags = {"a","b"};
  
  @ToString.Exclude 
  private int id = 99;
  private int age = 100;
  
  public static void main(String[] args) {
	System.out.println(new ToStringExample());
}
  
  @ToString(callSuper=true, includeFieldNames=true)
  public static class Square{
    private final int width, height;
    
    public Square(int width, int height) {
      this.width = width;
      this.height = height;
    }
  }
}