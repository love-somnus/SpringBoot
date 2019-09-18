package com.somnus.designPatterns.singleton;

import java.io.Serializable;

//懒汉式本身是非线程安全的，为了实现线程安全有几种写法，分别是下面的2、3，
public class Singleton implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static Singleton instance ;
	
	private Singleton(){}
	
	public static Singleton getInstance(){
		if(instance == null){
			instance = new Singleton();
		}
		return instance;
	}
}

//懒汉式-升级写法
class Singleton2{
	private static Singleton2 instance ;
  
	private Singleton2(){}
  
	public static synchronized Singleton2 getInstance(){
		if(instance == null){
			instance = new Singleton2();
		}
		return instance;
	}
}

//懒汉式-进一步升级写法
class Singleton3{
	private static Singleton3 instance ;
  
	private Singleton3(){}
  
	public static Singleton3 getInstance(){
		if(instance == null){
			synchronized (Singleton3.class) {  
				instance = new Singleton3();   
			} 
		}
		return instance;
	}
}
//懒汉式的最终升级版变种到了双重检测锁
//双重检测锁【在JDK1.5之后，双重检查锁定才能够正常达到单例效果。】
class Singleton4{
  private static Singleton4 instance ;
  
  private Singleton4(){}
  
  public static Singleton4 getInstance(){
      //第一重判断  
      if (instance == null) {
          //锁定代码块  
          synchronized (Singleton4.class) {
              //第二重判断  
              if (instance == null) {
                  instance = new Singleton4(); //创建单例实例  
              }
          }
      }
      return instance;
  }
}

//静态(static)内部类【线程安全，调用效率高，但是，可以延时加载】
class Singleton6 implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Singleton6(){}

	private static class HolderClass {
		private final static Singleton6 instance = new Singleton6();
	}
	        
	public static Singleton6 getInstance() {
		return HolderClass.instance;
	}
}

//饿汉式【线程安全，调用效率高，但是，不能延时加载】
class Singleton5{
	//类初始化时，立即加载这个对象（没有延时加载的优势），加载类时，天然是线程安全的
	//饿汉式在类创建的同时就已经创建好一个静态的对象供系统使用，以后不再改变，所以天生是线程安全的。
	private static Singleton5 instance = new Singleton5();
	
	private Singleton5(){}
	
	//方法没有同步，调用效率高
	public static Singleton5 getInstance(){
		return instance;
	}
}

//枚举式【线程安全，调用效率高，但是，不能延时加载】
enum Singleton7{
	
	INSTANCE;
	
	private Singleton7(){}
	
	public void opreate(){
		
	}
}

/** ※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※*/

class Singleton8{
	private static ThreadLocal<Singleton8> instance = new ThreadLocal<Singleton8>();
	
	private Singleton8(){}
	
	public static Singleton8 getInstance(){
		Singleton8 context = instance.get();
    	if(context == null){
    		context = new Singleton8();
    		instance.set(context);
    	}
        return context;
    }
}
