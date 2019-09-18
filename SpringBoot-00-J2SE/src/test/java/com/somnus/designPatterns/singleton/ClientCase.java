package com.somnus.designPatterns.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang.SerializationUtils;

public class ClientCase {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Singleton6 s = Singleton6.getInstance();
		Singleton6 s_ = Singleton6.getInstance();
		System.out.println(s);
		System.out.println(s_);
		
		//通过反射破解
		Class<Singleton6> clazz = (Class<Singleton6>) Class.forName("com.somnus.designPatterns.singleton.Singleton6");
		Constructor<Singleton6> c = clazz.getDeclaredConstructor(null);
		c.setAccessible(true);
		Singleton6 s1 = c.newInstance();
		Singleton6 s2 = c.newInstance();
		System.out.println(s1 == s2);
		
		//通过序列化/反序列化破解
		Singleton6 s3 = (Singleton6) SerializationUtils.clone(s);
		System.out.println(s3);
	}

}
