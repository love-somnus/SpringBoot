package com.somnus.springboot.pay.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.somnus.springboot.pay.enums.PayChannel;
import com.somnus.springboot.pay.pojo.ConfirmResult;
import com.somnus.springboot.pay.pojo.PaymentOrder;
import com.somnus.springboot.pay.pojo.PaymentResult;
import com.somnus.springboot.pay.service.PaymentChannelService;
import com.somnus.springboot.pay.thirdpay.PaymentChannelHandler;
import com.somnus.springboot.pay.thirdpay.PaymentChannelHandlerAdapter;
import com.somnus.springboot.pay.thirdpay.RequestParameter;
import com.somnus.springboot.pay.thirdpay.RequestType;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PaymentChannelServiceImpl implements PaymentChannelService {

    @Resource
    private PaymentChannelHandlerAdapter handlerAdapter;

    protected <P, R> PaymentChannelHandler getHandler(RequestParameter<P, R> parameter) {
        log.info("处理支付请求:{}", parameter);
        Assert.isNull(parameter.getChannel(), "PAY_CHANNEL_IS_NULL");
        Assert.isNull(parameter.getType(), "REQUEST_TYPE_IS_NULL");
        Assert.isNull(parameter.getParameter(), "PAYMENT_PARAMETER_IS_NULL");
        PaymentChannelHandler handler = handlerAdapter.getHandler(parameter);
        Assert.isNull(handler, "PAYMENT_HANDLER_NOT_FOUND");
        return handler;
    }

    private <P> String handleCallback(RequestParameter<P, String> parameter) {
        PaymentChannelHandler handler = this.getHandler(parameter);
        try {
            return handler.handle(parameter);
        } catch (Exception e) {
            log.warn("处理支付前端回调请求失败", e);
        }
        return handler.getFailedResponse(parameter);
    }

    @Override
    public String createOrder(PaymentOrder paymentOrder) {
        RequestParameter<PaymentOrder, String> parameter = new RequestParameter<PaymentOrder, String>(PayChannel.valueOf(paymentOrder.getThirdPayType()), RequestType.ORDER, paymentOrder);
        PaymentChannelHandler handler = this.getHandler(parameter);
        return handler.handle(parameter);
    }

    @Override
    public String handleReturn(PayChannel channel, Map<String, Object> parameter) {
        return handleCallback(new RequestParameter<>(channel, RequestType.RETURN, parameter));
    }

    @Override
    public String handleReturn(PayChannel channel, String parameter) {
        return handleCallback(new RequestParameter<>(channel, RequestType.RETURN, parameter));
    }

    @Override
    public String handleNotify(PayChannel channel, Map<String, Object> parameter) {
        return handleCallback(new RequestParameter<>(channel, RequestType.NOTIFY, parameter));
    }

    @Override
    public String handleNotify(PayChannel channel, String parameter) {
        return handleCallback(new RequestParameter<>(channel, RequestType.NOTIFY, parameter));
    }

    @Override
    public String handleRefund(PayChannel channel, Map<String, String> parameter) {
        return handleCallback(new RequestParameter<Map<String, String>, String>(channel, RequestType.REFUND, parameter));
    }

    @Override
    public Map<String, String> queryOrder(PaymentOrder paymentOrder) {
        RequestParameter<String, Map<String, String>> parameter = new RequestParameter<String, Map<String,String>>();
        parameter.setChannel(PayChannel.valueOf(paymentOrder.getThirdPayType()));
        parameter.setType(RequestType.QUERY);
        parameter.setParameter(paymentOrder.getOrderId());

        PaymentChannelHandler handler = this.getHandler(parameter);

        Map<String, String> result = handler.handle(parameter);

        log.info("订单[{}]支付结果:{}", paymentOrder.getOrderId(), result);

        return result;
    }

    @Override
    public ConfirmResult confirmOrder(PaymentOrder paymentOrder) {
        RequestParameter<String, PaymentResult> parameter = new RequestParameter<String, PaymentResult>();
        parameter.setChannel(PayChannel.valueOf(paymentOrder.getThirdPayType()));
        parameter.setType(RequestType.CONFIRM);
        parameter.setParameter(paymentOrder.getOrderId());

        PaymentChannelHandler handler = this.getHandler(parameter);

        PaymentResult result = handler.handle(parameter);

        log.info("订单[{}]支付结果:{}", paymentOrder.getOrderId(), result);

        return new ConfirmResult(result.isPay(), result);
    }


}
