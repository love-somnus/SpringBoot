package com.somnus.springboot.pay.pojo;

import java.io.Serializable;

import lombok.Data;

@Data
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private int               code             = -1; // 返回状态码
    private boolean           ok;                   // 返回状态
    private Object            data;                 // 返回信息对象

}
