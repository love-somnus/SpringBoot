package com.somnus.lombok;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author kevin.liu
 * @title: NullableExample
 * @projectName SpringBoot
 * @description: TODO
 * @date 2022/6/24 16:48
 */
@AllArgsConstructor
public class NullableExample {

    @NonNull
    private String username;

    @Nullable
    private String password;

    @Nullable
    public String get(@Nullable String echo){

        return this.username + "|" + this.password + "|" + echo;
    }

    public static void main(String[] args) {
        NullableExample c = new NullableExample("kk", null);
        System.out.println(c.get(null));
    }
}
