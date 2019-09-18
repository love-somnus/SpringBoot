package com.somnus.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 实现带有构造器的枚举
 *
 * @author Somnus
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum GenderEnum {
    //通过括号赋值,而且必须带有一个参构造器和一个属性跟方法，否则编译出错
    //赋值必须都赋值或都不赋值，不能一部分赋值一部分不赋值；如果不赋值则不能写构造器，赋值编译也出错
    MAN(0, "男"), WOMEN(1, "女");

    @Getter
    public final int code;

    @Getter
    public final String desc;

    /**
     * 通过枚举<code>code</code>获得枚举
     *
     * @param code
     * @return
     */
    public static GenderEnum codeOf(int code) {
        for (GenderEnum value : GenderEnum.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }

    public static GenderEnum descOf(String desc) {
        for (GenderEnum value : GenderEnum.values()) {
            if (value.getDesc().equals(desc)) {
                return value;
            }
        }
        return null;
    }

    public static GenderEnum nameOf(String name) {
        GenderEnum enumm = null;
        if (name != null){
            for (GenderEnum value : GenderEnum.values()) {
                if (value.name().equalsIgnoreCase(name)){
                    enumm = value;
                }
            }
        }
        return enumm;
    }
}
