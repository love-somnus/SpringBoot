package com.somnus.lombok;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * <p>
 * level：指定变量的访问修饰符且不能为 NONE，默认值：NONE
 * 共有 PUBLIC、MODULE、PROTECTED、PACKAGE、PRIVATE、NONE
 * makeFinal：是否加 final，默认 false，如果为 true 则所有的实例变量必须初始化
 * </p>
 * @author kevin.liu
 * @title: FieldDefaultsExample
 * @projectName SpringBoot
 * @description: TODO
 * @date 2022/6/24 15:47
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FieldDefaultsExample {
    String name = "古力娜扎";
}
