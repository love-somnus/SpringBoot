package com.somnus.springboot.pay.service;

import com.somnus.springboot.pay.pojo.PaymentOrder;
import com.somnus.springboot.pay.pojo.PaymentResult;

/**
 * @ClassName:     PaymentService.java
 * @Description:   支付服务(不带事务控制)
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年12月28日 下午4:15:50
 */
public interface PaymentService {

    /**
     * 支付核心处理逻辑
     * @param paymentOrder
     * @return
     */
    public String pay(PaymentOrder paymentOrder);

    /**
     * 确认并更新订单的支付结果
     * @param orderId
     * @return
     */
    public boolean confirmAndUpdateOrder(String orderId);

    /**
     * 更新订单为支付成功
     * @param paymentResult
     */
    public void updateOrder2Success(PaymentResult paymentResult);

    /**
     * 兼容老业务判断sourceId是否可以使用帮钻支付(帮钻充值等)
     * @param sourceId
     * @return
     */
    public boolean isBZEnabledSoucreIds(Integer source);


}
