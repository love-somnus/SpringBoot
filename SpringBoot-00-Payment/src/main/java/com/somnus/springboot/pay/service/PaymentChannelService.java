package com.somnus.springboot.pay.service;

import java.util.Map;

import com.somnus.springboot.pay.enums.PayChannel;
import com.somnus.springboot.pay.pojo.ConfirmResult;
import com.somnus.springboot.pay.pojo.PaymentOrder;

/**
 * @ClassName:     PaymentChannelService.java
 * @Description:   支付回调服务接口
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年12月28日 下午4:36:59
 */
public interface PaymentChannelService {

    /**
     * 构建第三方支付页面跳转表单
     * @param paymentOrder 支付订单信息
     * @return 跳转表单
     */
    public String createOrder(PaymentOrder paymentOrder);

    public String handleReturn(PayChannel channel, Map<String, Object> parameter);

    public String handleReturn(PayChannel channel, String parameter);

    public String handleNotify(PayChannel channel, Map<String, Object> parameter);

    public String handleNotify(PayChannel channel, String parameter);

    public String handleRefund(PayChannel channel, Map<String, String> parameter);

    /**
     * 查询第三方支付订单详情
     * @param paymentOrder 本地支付订单信息
     * @return 第三方支付订单详情
     */
    public Map<String, String> queryOrder(PaymentOrder paymentOrder);

    /**
     * 确认订单支付是否成功
     * @param paymentOrder 本地支付订单信息
     * @return 確認結果
     */
    public ConfirmResult confirmOrder(PaymentOrder paymentOrder);

}
