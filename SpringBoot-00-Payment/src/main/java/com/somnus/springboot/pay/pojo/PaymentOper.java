package com.somnus.springboot.pay.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
/*@Table(name = "t_user_payment_oper")*/
public class PaymentOper implements Serializable {

    private static final long serialVersionUID = -7177156497705541402L;
    
    private Integer id;
    private String operatorId; // 发起请求的操作人
    private String payId; // 支付号
    private Integer thirdPayType;  // 第三方支付支付渠道
    private String thirdTradeNo; // 第三方支付平台订单号
    private Double thirdTradeAmount; // 第三方支付平台支付金额
    private Integer status; // 支付状态
    private Date createTime;
    private Date updateTime;
    private String operatorMsg;// 操作描述
    private int operatorType = 0; //操作类型，（默认为0代表CRM手动调用）
    private String memo; //备注

}
