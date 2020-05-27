package com.somnus.copy;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.lang3.time.StopWatch;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2018/12/13 12:38
 */
public class CopyCase {

    private StopWatch sw = new StopWatch();

    @Before
    public void setUp() {
        sw.start();
    }

    @Test
    public void deepCopy(){
        Person person = new Person(Lists.newArrayList("1.jpg","2.jpg"),Lists.newArrayList(new Person.Pet(("dog"))));
        People people = new People();
        people.setBirthday(new Date());
        org.springframework.beans.BeanUtils.copyProperties(person, people);
        System.out.println(person);
        people.setMoney(BigDecimal.TEN);
        System.out.println(people);
    }

    @Test
    public void springBeanUtils(){
        for(int i=0; i<100000; i++){
            Person person = new Person("admin","password",new Date(), BigDecimal.TEN);
            People people = new People();
            org.springframework.beans.BeanUtils.copyProperties(person, people);
        }
    }

    @Test
    public void apacheBeanUtils() throws IllegalAccessException, InvocationTargetException {
        for(int i=0; i<100000; i++){
            Person person = new Person("admin","password",new Date(),BigDecimal.TEN);
            People people = new People();
            org.apache.commons.beanutils.BeanUtils.copyProperties(people, person);
        }
    }

    @Test
    public void apachePropertyUtils() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
        for(int i=0; i<100000; i++){
            Person person = new Person("admin","password",new Date(),BigDecimal.TEN);
            People people = new People();
            org.apache.commons.beanutils.PropertyUtils.copyProperties(people, person);
            System.out.println(people);
        }
    }

    @Test
    public void cglibPropertyUtils() {
        for(int i=0; i<100000; i++){
            Person person = new Person("admin","password",new Date(),BigDecimal.TEN);
            People people = new People();
            net.sf.cglib.beans.BeanCopier bc = BeanCopier.create(Person.class, People.class,false);
            bc.copy(person, people, null);
        }
    }

    @After
    public void tearDown() {
        sw.stop();
        System.out.println("共消耗：" + sw.getTime());
    }
}
