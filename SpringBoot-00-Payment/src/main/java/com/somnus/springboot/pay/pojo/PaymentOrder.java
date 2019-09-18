package com.somnus.springboot.pay.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.somnus.springboot.pay.enums.PaymentOrderType;

import lombok.Data;

@Data
/*@Table(name = "t_user_payment_order")*/
public class PaymentOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    
    private String  orderId; // 订单号信息，用来支持多订单的方式,多个订单号使用","分割
    
    private String  userId; // 用户UUID
    
    private String  subject       = ""; // 提交主题
    
    private BigDecimal  amount; // 订单总金额
    
    private BigDecimal  finalAmount; // 订单回传金额，支付平台回传
    
    private BigDecimal  totalAmount; // 订单总金额，包含混合支付帮豆金额数
    
    private Integer status        = PaymentOrderType.NOTDONE.getValue(); // 支付状态
    
    private Integer source; // 支付来源,用来却分是普通代码还是VIP支付，还是充值
    
    private Integer thirdPayType; // 支付平台类型
    
    private String  memo;
    
    private String  thirdTradeNo; // 第三方支付平台号，自定义
    
    private Date    createTime;
    
    private Date    updateTime; // 订单支付时间
    
    private String  defaultBank; // 支付选择的银行类型
    
    private String  bankBillNo; // 银行对账单
    
    private String  inviterId;
    
    private Integer freeFeeType; // VIP的类型、1黄金 2白金
    
    private String  parentOrderId = ""; // 合并付款的批量支付ID,默认""，即单个订单;xxxxx表示多个订单
    
    private String  trafficSource; // 订单流量来源
    
    private BigDecimal fee; //运费
    
    private BigDecimal tax; //税款
    
    private BigDecimal goodsAmount; //货款金额

    private String  sourceKey; // 校验Key
    private String  sign; // 订单校验签名
    private String  buyerId;
    private String  address;
    private String  mobileNum; // 充值的电话号码
    private String  chargeValue; // 手机充值的金额
    private Boolean isCombined    = false; // 是否合并付款 0：不是(默认)，1：是
    private String  amountDetail  = ""; // 合并订单的详细价格信息,多个订单价格使用","分割
    private Integer isAllowAliPay = 0; // 是否可用支付宝支付 0：否(默认)，1：是
    private String code;	//微信用户授权后返回的code
    private String scene; // 用于支付场景
    private String payFrom; // 支付来源
    private String imei;
    private Integer crossPay = 0; //是否需要境外支付0为国内，1为境外（香港），2为境外（韩国）
    private String addressId; //用户中心地址Id
    private Date startTime;
    private Date endTime;

    //报关需要下单时使用
    private String idCardName; //下单者身份证对应姓名
    private String idCard; //下单者身份证号
    private String addressInfo; //下单者地址信息

}
