package com.somnus.springboot.pay.service;

import java.util.Map;

import com.somnus.springboot.pay.pojo.Page;
import com.somnus.springboot.pay.pojo.PaymentOrder;
import com.somnus.springboot.pay.pojo.PaymentResult;
import com.somnus.springboot.pay.pojo.QueryResult;

public interface PaymentOrderService {

    public PaymentOrder get(String orderId, int source);

    public PaymentOrder get(String orderId);

    public PaymentResult convert(PaymentOrder paymentOrder);

    public QueryResult<PaymentOrder> list(Page page,Map<String,Object> params);

    public PaymentOrder get(String orderId, String userId);

}
