package com.somnus.generic;

import java.util.List;

/**
 * @author Somnus
 * @version V1.0
 * @Description: TODO
 * @date 2016年1月11日 下午4:47:16
 */
public class GenericDemo {

    public static void main(String[] args) {
        Class<Animal> clazz = Animal.class;
        Object o = new Animal();
        System.out.println(clazz.cast(o));
        Object md = null;
        System.out.println((String)md);
    }

    private List<?> result;

    private List<? extends Animal> list;

    private List<? super Cat> list2;

    /*public void add(<? extends Animal> a){
        
    }*/
    /*public void add(<? super Cat> a){
        
    }*/
    public void add(List<?> list) {

    }

    public Animal save(List<? extends Animal> list) {
        if (list != null && list.size() > 0) {
            Animal animal = Animal.class.cast(list.get(0));
            return animal;
        }
        return null;
    }

    public Animal save2(List<? super Cat> list) {
        if (list != null && list.size() > 0) {
            Animal animal = Animal.class.cast(list.get(0));
            return animal;
        }
        return null;
    }

    public List<?> getResult() {
        return result;
    }

    public void setResult(List<?> result) {
        this.result = result;
    }

    public List<? extends Animal> getList() {
        return list;
    }

    public void setList(List<? extends Animal> list) {
        this.list = list;
    }

    public List<? super Cat> getList2() {
        return list2;
    }

    public void setList2(List<? super Cat> list2) {
        this.list2 = list2;
    }
}

class Animal {

}

class Cat extends Animal {

}