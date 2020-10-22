package com.somnus.reflect;

public class People extends China{
	private String sex = "male";

	public double height = 10.0;

	public People(String sex) {
		super();
		this.sex = sex;
	}

	public People() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void sayChina() {
		System.out.println("hello ,china"); 
	}

	public void sayHello(String name, int age) {
		System.out.println(name+"  "+age); 
	}

	private void love(){
		System.out.println("love ,love");
	}
}
