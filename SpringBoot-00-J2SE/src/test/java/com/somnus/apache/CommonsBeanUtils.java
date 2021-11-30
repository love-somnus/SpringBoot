package com.somnus.apache;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.junit.Test;

import com.somnus.apache.Person.Pet;

/**
 * @author Somnus
 * @version V1.0
 * @Title: CommonsBeanUtils.java
 * @Package com.somnus.apache
 * @Description: TODO
 * @date 2015年6月12日 下午5:38:24
 */
public class CommonsBeanUtils {

    static {
        BigDecimalConverter bdc = new BigDecimalConverter(null);
        ConvertUtils.register(bdc, java.math.BigDecimal.class);

        DateConverter dc = new DateConverter(null);
        String[] patterns = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", };
        dc.setPatterns(patterns);
        //Date顺利被转换成String
        ConvertUtils.register(dc, String.class);
        //String顺利被转换成Date(null值也支持)
        ConvertUtils.register(dc, java.util.Date.class);
    }

    @Test
    public void cloneBean() throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        Person person = new Person("admin", "password", new Date(), BigDecimal.TEN, new Pet("diu diu"));

        /** 浅拷贝 **/
        Person person2 = (Person) BeanUtils.cloneBean(person);
        person2.getPet().setName("mie mie");

        System.out.println("是否相同？ " + (person == person2));

        System.out.println("宠物是否相同？ " + (person.getPet() == person2.getPet()));

        System.out.println(person.getPet() + "||" + person2.getPet());
        System.out.println(person.getPet().getName() + "||" + person2.getPet().getName());
    }

    @Test
    public void populate() throws IllegalAccessException, InvocationTargetException {
        Map<String, Object> map = new HashMap<>();
        map.put("username", "tom");
        map.put("password", "tom@");
        map.put("birthday", "2019-07-31");
        map.put("high", "12");
        map.put("money", "100.00");
        map.put("pet", new Pet("aaaaaa"));
        /*将map转化为一个Person对象*/
        Person person = new Person();
        BeanUtils.populate(person, map);
        System.out.println("将map转化为一个Person对象>>" + person);

    }

    @Test
    public void describe() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Person person2 = new Person("admin", "password", new Date(), null, null);
        /*将一个Bean转化为一个Map对象了，如下：key是对象的属性名，value是属性取值对象所得到的字符串，不能进行深层复制*/
        Map<String, String> map2 = BeanUtils.describe(person2);
        System.out.println(map2.get("username") + ">>" + map2.get("birthday"));
    }

    @Test
    public void describe2() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Person person2 = new Person("admin", "password", new Date(), null, null);
        /*将一个Bean转化为一个Map对象了，如下：key是对象的属性名，value仍然是属性的原始取值，不做字符串转换。换句话说，它是可以进行深层对象复制转换的*/
        Map<String, Object> map2 = PropertyUtils.describe(person2);
        System.out.println(map2.get("username") + ">>" + map2.get("birthday"));
    }

    /**
     * org.springframework.beans.BeanUtils.copyProperties 是一个Spring提供的名称相同的工具类
     * 但它不支持类型自动转换，如果某个类型属性不同，则不予转换那个属性
     */
    @Test
    public void copyProperties() throws IllegalAccessException, InvocationTargetException {
        Person person = new Person(null, "password", new Date(), null, null);
        /*拥有相同属性的对象转换-->支持属性类型自动转换的功能*/
        People people = new People("lucy", "china");
        /*BeanUtils在对Bean赋值时会进行类型转化 */
        BeanUtils.copyProperties(people, person);
        System.out.println("拥有相同属性的对象转换>>" + people);
    }

    @Test
    public void copyProperties2() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Person person = new Person("admin", "password", new Date(), null, null);
        /*拥有相同属性的对象转换-->不支持属性类型自动转换的功能*/
        People people = new People("lucy", "china");
        /*PropertyUtils不会对类型进行转化，如果类型不同则会抛出异常 */
        PropertyUtils.copyProperties(people, person);
        System.out.println("拥有相同属性的对象转换>>" + people);
    }

    @Test
    public void reflect() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Person person = new Person("admin", "password", null, null, null);
        /*反射调用get方法*/
        String username = (String) PropertyUtils.getProperty(person, "username");
        System.out.println(username);
        /*反射调用set方法*/
        PropertyUtils.setProperty(person, "high", 25.0);
        System.out.println(person);
    }

    @Test
    public void reflect2() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Person person = new Person("admin", "password", null, null, null);
        MethodUtils.invokeMethod(person, "setUsername", new Object[]{"andy"});
        String name = (String) MethodUtils.invokeMethod(person, "getPassword", new Object[]{});
        System.out.println(name);
        System.out.println(person);
    }
}
