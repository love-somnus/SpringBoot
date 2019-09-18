package com.somnus.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.somnus.springboot.service.TransactionService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TransactionBaseServiceTest {

    @Autowired
    private TransactionService cityService;

    @Test
    public void inserSuccess1st(){
        cityService.inserSuccess1st();
    }

    @Test
    public void inserFail1st(){
        cityService.inserFail1st();
    }

    @Test
    public void inserSuccess2nd() throws Exception{
        cityService.inserSuccess2nd();
    }

    @Test
    public void inserFail2nd() throws Exception{
        cityService.inserFail2nd();
    }

}
