package com.somnus.springboot.pay.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import lombok.Data;

@Data
public class PaymentMessage implements Serializable {

    private static final long serialVersionUID = -7029073613969098739L;
    private String  payId; // 订单号信息，用来支持多订单的方式,多个订单号使用","分割
    private String  userId; // 用户UUID
    private Integer status= 0; // 支付状态
    private String  thirdTradeNo; // 第三方支付平台号，自定义
    private Integer thirdPayType  = 0; // 支付平台类型
    private String  parentOrderId = ""; // 合并付款的批量支付ID,默认""，即单个订单;xxxxx表示多个订单
    private String payFrom; // 支付来源
    private String imei;
    private Long  superBz   = 0l;
    private String sign;
    private Boolean isCombined    = false; // 是否合并付款 0：不是(默认)，1：是
    private String  subject       = ""; // 提交主题
    private Double  amount        = 0.0; // 订单总金额
    private Double  finalAmount   = 0.00; // 订单回传金额，支付平台回传
    private Double  totalAmount   = 0.00; // 订单总金额，包含混合支付帮豆金额数
    private Integer source        = 0; // 支付来源,用来却分是普通代码还是VIP支付，还是充值
    // 做VIP购买时
    private String price;
    private Long payTime;
    private Integer freeFeeType; // VIP的类型、1黄金 2白金
    private String inviterId;
    private Integer messageType; //消息类型 0：帮5采   1：其他（暂时只区分是否是帮5采）

    /**
     * 封装老回调代码
     * @return
     */
    public Map<String, Object> toNotifyParamsMap() {
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("orderId", payId);
        paramsMap.put("userId", userId);
        paramsMap.put("status", status);
        paramsMap.put("thirdTradeNo", thirdTradeNo);
        paramsMap.put("parentOrderId", parentOrderId);
        paramsMap.put("payFrom", payFrom);
        paramsMap.put("thirdPayType", thirdPayType);
        paramsMap.put("imei", imei);
        paramsMap.put("finalAmount", finalAmount);
        paramsMap.put("superBz", superBz);
        // 做VIP购买时
        if ( null != source && (source == 225 || source == 226 || source == 228) && null != freeFeeType && (freeFeeType == 1 || freeFeeType == 2)) {
            paramsMap.put("price", price);
            paramsMap.put("payTime", payTime == null ? new Date().getTime() : payTime);
            paramsMap.put("type", freeFeeType);
            if(!StringUtils.isEmpty(inviterId)){
                paramsMap.put("inviterId", inviterId);
            }
        }
        paramsMap.put("sign", sign);
        return paramsMap;
    }

}
