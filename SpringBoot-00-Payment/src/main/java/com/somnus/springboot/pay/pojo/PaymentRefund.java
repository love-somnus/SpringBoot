package com.somnus.springboot.pay.pojo;

import java.util.Date;

import lombok.Data;

@Data
/*@Table(name = "t_user_payment_refund")*/
public class PaymentRefund {

	private Integer id;
	private String operatorId; // 发起请求的操作人
	private String orderNum; // 订单接口的订单号
	private String orderId; // 支付号
	private Integer thirdPayType;  // 支付渠道
	private String thirdTradeNo; // 第三方订单号
	private Double refundAmount; // 退款金额
	private Integer refundStatus; // #退款状态 0 | 1 退款成功 | -1 退款失败
	private String refundMsg ;
	private Date createTime;
	private Date updateTime;
	
}
