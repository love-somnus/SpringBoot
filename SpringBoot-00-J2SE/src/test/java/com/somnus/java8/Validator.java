package com.somnus.java8;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @author kevin.liu
 * @title: Validator
 * @projectName SpringBoot
 * @description: TODO
 * @date 2021/12/14 10:58
 */
public class Validator<T> {
    /**
     * 初始化为 true  true &&其它布尔值时由其它布尔值决定真假
     */
    private Predicate<T> predicate = t -> true;

    private final T value;

    private Validator() {
        this.value = null;
    }

    private Validator(T value) {
        this.value = Objects.requireNonNull(value);
    }

    public static <T> Validator<T> of(T value) {
        return value == null ? new Validator<>() : new Validator<>(value);
    }

    /**
     * 添加一个校验策略，可以无限续杯😀
     *
     * @param predicate the predicate
     * @return the validator
     */
    public Validator<T> with(Predicate<T> predicate) {
        this.predicate = this.predicate.and(predicate);
        return this;
    }

    /**
     * 执行校验
     *
     * @return the boolean
     */
    public boolean validate() {
        return Objects.nonNull(value) && predicate.test(value);
    }

    public static void main(String[] args) {
        String arg = "lambada&.com";
        boolean check = Validator.of(arg)
                .with(s -> s.length() > 5)
                .with(s -> s.matches("[^%&',;=?$\\x22]+"))
                .with(s -> s.startsWith("lambada"))
                .validate();
        System.out.println(check);
    }
}
