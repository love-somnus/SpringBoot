package com.somnus.enums;

public class Test {

    @org.junit.Test
    public void equal(){
        System.out.println(GenderEnum.MAN.equals(GenderEnum.MAN));
        System.out.println(GenderEnum.MAN.equals(GenderEnum.WOMEN));

        System.out.println(GenderEnum.MAN.equals(GenderEnum.codeOf(1)));

        System.out.println(GenderEnum.MAN.getCode() == 1);
    }

}
