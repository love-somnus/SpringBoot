package com.somnus.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.Test;

public class ReflectClient {
    /**
     * 通过一个对象获得完整的包名和类名
     */
    @Test
    public void reflect01() {
        ReflectClient demo = new ReflectClient();

        System.out.println(demo.getClass().getName());

        System.out.println(ReflectClient.class.getName());

        System.out.println(ReflectClient.class.getSimpleName());
    }

    @Test
    public void reflect02() throws ClassNotFoundException {
        Class<?> demo1 = Class.forName("com.somnus.somnus.reflect.Demo");
        Class<?> demo2 = new ReflectClient().getClass();
        Class<?> demo3 = ReflectClient.class;
        System.out.println("类名称" + demo1.getName());
        System.out.println("类名称" + demo2.getName());
        System.out.println("类名称" + demo3.getName());
    }

    @Test
    public void reflect03() throws Exception {
        Class<?> demo = Class.forName("com.somnus.reflect.Person");
        Person person = (Person) demo.newInstance();
        person.setName("jay");
        person.setAge(11);
        System.out.println(person);
    }

    @Test
    public void reflect04() throws Exception {
        Class<?> demo = Class.forName("com.somnus.reflect.Person");

        Person person = (Person) demo.getConstructor(new Class[]{String.class}).newInstance(new Object[]{"Rollen"});
        System.out.println(person);
    }

    @Test
    public void reflect05() throws ClassNotFoundException {
        Class<?> demo = Class.forName("com.somnus.reflect.People");
        //保存所有的接口
        Class<?> intes[] = demo.getInterfaces();

        for (int i = 0; i < intes.length; i++) {
            System.out.println("实现的接口   " + intes[i].getName());
        }
    }

    @Test
    public void reflect06() throws ClassNotFoundException {
        Class<?> demo = Class.forName("com.somnus.reflect.People");
        //取得父类         
        Class<?> temp = demo.getSuperclass();
        System.out.println("继承的父类为：   " + temp.getName());
    }

    @Test
    public void reflect07() throws ClassNotFoundException {
        Class<?> demo = Class.forName("com.somnus.reflect.People");
        Constructor<?> cons[] = demo.getConstructors();
        for (int i = 0; i < cons.length; i++) {
            System.out.println("构造方法：  " + cons[i]);
        }
    }

    @Test
    public void reflect08() throws ClassNotFoundException {
        Class<?> demo = Class.forName("com.somnus.reflect.People");
        Constructor<?> cons[] = demo.getConstructors();
        for (int i = 0; i < cons.length; i++) {
            Class<?> p[] = cons[i].getParameterTypes();
            System.out.print("构造方法：  ");
            // 权限修饰符
            int mo = cons[i].getModifiers();
            System.out.print(Modifier.toString(mo) + " ");
            //构造方法名字
            String consName = cons[i].getName();
            System.out.print(consName + "(");
            for (int j = 0; j < p.length; ++j) {
                //参数类型名字
                String typeName = p[j].getName();
                System.out.print(typeName + " arg" + i);
                if (j < p.length - 1) {
                    System.out.print(",");
                }
            }
            System.out.println("){}");
        }
    }

    @Test
    public void reflect09() throws ClassNotFoundException {
        Class<?> demo = Class.forName("com.somnus.reflect.People");
        System.out.println("===============本类属性========================");
        // 取得本类的全部属性
        Field[] field = demo.getDeclaredFields();
        for (int i = 0; i < field.length; i++) {
            // 权限修饰符
            int mo = field[i].getModifiers();
            String priv = Modifier.toString(mo);
            // 属性类型
            Class<?> type = field[i].getType();
            //属性字段名字
            String name = field[i].getName();
            System.out.println(priv + " " + type.getName() + " " + name + ";");
        }
        System.out.println("===============实现的接口或者父类的属性========================");
        // 取得实现的接口或者父类的属性
        Field[] field1 = demo.getFields();
        for (int j = 0; j < field1.length; j++) {
            // 权限修饰符
            int mo = field1[j].getModifiers();
            String priv = Modifier.toString(mo);
            // 属性类型
            Class<?> type = field1[j].getType();
            //属性字段名字
            String name = field1[j].getName();
            System.out.println(priv + " " + type.getName() + " " + name + ";");
        }
        System.out.println("===============本类方法========================");
        Method[] methods = demo.getDeclaredMethods();
        for (Method method : methods){
            System.out.println(method.getName() + "->" + method.getReturnType());
        }
        System.out.println("===============实现的接口或者父类的方法========================");
        Method[] methods1 = demo.getMethods();
        for (Method method : methods1){
            System.out.println(method.getName() + "->" + method.getReturnType());
        }
    }

    @Test
    public void reflect10() throws Exception {
        Class<?> demo = Class.forName("com.somnus.reflect.People");
        // getMethod：获取当前类和父类的所有public的方法。
        Method method = demo.getMethod("sayChina");
        method.invoke(demo.newInstance());

        // getDeclaredMethod：获取当前类的所有声明的方法，包括public、protected和private修饰的方法。
        method = demo.getDeclaredMethod("sayHello", new Class[]{String.class, int.class});
        method.invoke(demo.newInstance(), new Object[]{"Rollen", 20});

        /*method = demo.getDeclaredMethod("sout");
        method.invoke(demo.newInstance());*/

        ///////////////////////////////////////////////////////////////////////////////////////
        // getField：获取当前类和父类的所有public的属性。
        Field field = demo.getField("height");
        System.out.println(field.get(demo.newInstance()));
        field = demo.getField("age");
        System.out.println(field.get(demo.newInstance()));

        /*field = demo.getField("name");//name是private属性，不是public属性
        System.out.println(field.get(demo.newInstance()));
        field = demo.getField("sex");//sex是private属性，不是public属性
        System.out.println(field.get(demo.newInstance()));*/

        // getDeclaredField：获取当前类的所有声明的方法，包括public、protected和private修饰的属性。
        field = demo.getDeclaredField("height");
        System.out.println(field.get(demo.newInstance()));
        field = demo.getDeclaredField("sex");
        field.setAccessible(true);
        System.out.println(field.get(demo.newInstance()));

        /*field = demo.getDeclaredField("age");//age是父类属性，不是当前类
        System.out.println(field.get(demo.newInstance()));
        field = demo.getDeclaredField("name");//name是父类属性，不是当前类
        System.out.println(field.get(demo.newInstance()));*/

    }

    @Test
    public void reflect11() throws Exception {
        Class<?> demo = null;

        Object obj = null;

        demo = Class.forName("com.somnus.reflect.People");

        obj = demo.newInstance();

        Field field = demo.getDeclaredField("sex");

        field.setAccessible(true);

        field.set(obj, "男");

        System.out.println(field.get(obj));
    }

    @Test
    public void reflect12(){
        int[] temp = {1, 2, 3, 4, 5};

        Class<?> demo = temp.getClass().getComponentType();

        System.out.println("数组类型： " + demo.getName());

        System.out.println("数组长度  " + Array.getLength(temp));

        System.out.println("数组的第一个元素: " + Array.get(temp, 0));

        Array.set(temp, 0, 100);

        System.out.println("修改之后数组第一个元素为： " + Array.get(temp, 0));
    }

}
