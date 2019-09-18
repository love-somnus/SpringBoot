package com.somnus.springboot.pay.pojo;

import java.io.Serializable;
import java.util.Date;

import com.somnus.springboot.pay.enums.SwitchType;

import lombok.Data;

/*@Table(name = "t_payment_switch")*/
@Data
public class Switch implements Serializable {

    private static final long serialVersionUID = 1L;

    private String key;
    private String value;
    private SwitchType type;
    private String memo;
    private Date createTime;
    private Date updateTime;


}
