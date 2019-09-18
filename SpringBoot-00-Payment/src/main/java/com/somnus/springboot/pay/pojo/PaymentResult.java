package com.somnus.springboot.pay.pojo;

import java.math.BigDecimal;

import com.somnus.springboot.pay.enums.PayChannel;
import com.somnus.springboot.pay.enums.PaymentOrderType;

import lombok.Data;

@Data
public class PaymentResult {

    private String orderId = null;
    private String tradeStatus;
    private PayChannel channel;
    private String thirdTradeNo;
    private BigDecimal price;
    private String buyerId;
    private String memo;
    private String bankType;
    private String bankBillNo;
    private String attach;
    // 支付失败时候的错误信息
    private String payInfo;
    // 0：普通订单 1：合并订单
    private int isCombined = 0;
    private int status = PaymentOrderType.SCCUESS.getValue();

    private boolean isPay;

}
