package com.somnus.lombok;

import lombok.Data;

/**
 * @author Somnus
 * @packageName com.somnus.lombok
 * @title: DataExample
 * @description: TODO
 * @date 2019/10/22 17:30
 */

/**
 * 自带
 * @Getter
 * @Setter
 * @ToString
 * @EqualsAndHashCode
 * @RequiredArgsConstructor
 */
@Data
public class DataExample {

    private String username;

    private String password;

    public static void main(String[] args) {
        DataExample data = new DataExample();
        data.setUsername("admin");
        data.setPassword("passw0rd");
        System.out.println(data);
    }
}
