package com.somnus.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.somnus.springboot.service.Transaction1stService;
import com.somnus.springboot.service.Transaction2ndService;
import com.somnus.springboot.service.Transaction3rdService;
import com.somnus.springboot.service.Transaction4thService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TransactionServiceTest {
	
	@Autowired  
    private Transaction1stService service1st;
	@Autowired  
    private Transaction2ndService service2nd;
	@Autowired  
    private Transaction3rdService service3rd;
	@Autowired  
    private Transaction4thService service4th;
	
	@Test
	public void service1st(){
		service1st.parent();
	}
	
	@Test
	public void service2nd(){
		service2nd.parent();
	}
	
	@Test
	public void service3rd(){
		service3rd.parent();
	}
	
	@Test
	public void service4th(){
		service4th.parent();
	}
}
