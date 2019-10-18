package com.somnus.designPatterns.prototype;

import java.io.IOException;
import java.io.OptionalDataException;

import org.junit.Test;

import com.somnus.designPatterns.prototype.WeeklyLog2.Attachment2;
import com.somnus.designPatterns.prototype.WeeklyLog3.Attachment3;

public class Client {
	
	@Test
	public void test() {
	    WeeklyLog log_previous = new WeeklyLog("张无忌", "第12周", "这周工作很忙，每天加班！");  //创建原型对象
       
        System.out.println(log_previous);
        
        System.out.println("--------------------------------");
       
        WeeklyLog  log_new = log_previous.clone(); //调用克隆方法创建克隆对象
        log_new.setDate("第13周");
        System.out.println(log_new);
	}
	
	@Test
	public void test2() {
        WeeklyLog2 log_previous = new WeeklyLog2(new Attachment2("study hard hard")); //创建原型对象
        
        WeeklyLog2 log_new  = log_previous.clone(); //调用克隆方法创建克隆对象
        log_new.getAttachment().setName("day day up");
        
        //比较周报
        System.out.println("周报是否相同？ " + (log_previous ==  log_new));
        
        //比较附件
        System.out.println("附件是否相同？ " +  (log_previous.getAttachment() == log_new.getAttachment()));
        
        System.out.println(log_previous.getAttachment() + "||" + log_new.getAttachment());
    }
	
	@Test
	public void test3() throws ClassNotFoundException, IOException {
        WeeklyLog3 log_previous = WeeklyLog3.builder().attachment(new Attachment3("study hard hard")).build(); // 创建原型对象
        
        WeeklyLog3 log_new = log_previous.deepClone(); // 调用深克隆方法创建克隆对象
        log_new.getAttachment().setName("day day up");
        
        // 比较周报
        System.out.println("周报是否相同？ " + (log_previous == log_new));
        
        // 比较附件
        System.out.println("附件是否相同？ " + (log_previous.getAttachment() == log_new.getAttachment()));
        
        System.out.println(log_previous.getAttachment() + "||" + log_new.getAttachment());
    }
}