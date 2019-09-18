package com.somnus.springboot.pay.thirdpay;

import java.io.Serializable;

import com.somnus.springboot.pay.enums.PayChannel;

/**
 * @ClassName:     RequestParameter.java
 * @Description:   支付渠道回调参数 
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年12月28日 上午10:20:34
 */
public class RequestParameter<P, R> implements Serializable {

    private static final long serialVersionUID = 1L;

    private PayChannel		channel;
    private RequestType		type;
    private P				parameter;
    private R				result;

    public RequestParameter(){}

    public RequestParameter(PayChannel channel, RequestType type, P parameter){
        this.channel = channel;
        this.type = type;
        this.parameter = parameter;
    }

    public PayChannel getChannel() {
        return channel;
    }

    public void setChannel(PayChannel channel) {
        this.channel = channel;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public P getParameter() {
        return parameter;
    }

    public void setParameter(P parameter) {
        this.parameter = parameter;
    }

    public R getResult() {
        return result;
    }

    public void setResult(R result) {
        this.result = result;
    }

}
