package com.somnus.reference;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2019/1/4 10:47
 */
@Data
@AllArgsConstructor
public class Apple {

    private String name;

    /**
     * 覆盖finalize，在回收的时候会执行。
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("Apple： " + name + " finalize。");
    }

    @Override
    public String toString() {
        return "Apple{" +
                "name='" + name + '\'' +
                '}' + ", hashCode:" + this.hashCode();
    }
}
