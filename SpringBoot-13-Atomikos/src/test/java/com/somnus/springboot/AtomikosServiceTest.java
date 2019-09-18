package com.somnus.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.somnus.springboot.service.AtomikosService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class AtomikosServiceTest {

    @Autowired
    private AtomikosService service;

    @Test
    public void saveSuccess(){
        service.saveSuccess();
    }

    @Test
    public void saveFail(){
        service.saveFail();
    }
}
