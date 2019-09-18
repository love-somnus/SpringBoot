package com.somnus.springboot.pay.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
/*@Table(name = "t_user_payment_config")*/
public class PaymentConfig implements Serializable {

    private static final long serialVersionUID = -3887045404428381695L;
    private Integer id; // 配置id
    private String keyName; // 配置关键字
    private String keyValue; // 配置内容
    private String memo; // 配置备注
    private Date createTime; // 配置创建时间
    private Date updateTime; // 最后更新时间
}
