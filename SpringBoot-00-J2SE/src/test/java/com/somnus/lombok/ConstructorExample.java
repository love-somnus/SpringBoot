/**
 * <p>Title: ConstructorExample.java </p>
 * <p>Description: TODO(用一句话描述该文件做什么) </p>
 *
 * @author pc
 * @version 1.0.0
 * @date 2018年9月21日
 */
package com.somnus.lombok;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName: ConstructorExample
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author pc
 * @date 2018年9月21日
 */
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE) // 自动生成无参数构造函数
@AllArgsConstructor//自动生成全参数构造函数
public class ConstructorExample {
    private String username;
    private String password;

    public static void main(String[] args) {
        System.out.println(new ConstructorExample());
        System.out.println(new ConstructorExample("somnus","654321"));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
