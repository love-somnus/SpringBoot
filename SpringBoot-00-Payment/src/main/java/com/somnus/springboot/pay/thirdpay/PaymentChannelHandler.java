package com.somnus.springboot.pay.thirdpay;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.somnus.springboot.pay.enums.PayChannel;
import com.somnus.springboot.pay.enums.PaymentOrderType;
import com.somnus.springboot.pay.pojo.Callback;
import com.somnus.springboot.pay.pojo.PaymentOrder;
import com.somnus.springboot.pay.pojo.PaymentResult;
import com.somnus.springboot.pay.service.CallbackService;
import com.somnus.springboot.pay.service.PayService;
import com.somnus.springboot.pay.service.PaymentChannelSwitchService;
import com.somnus.springboot.pay.service.PaymentOrderService;
import com.somnus.springboot.pay.support.common.ResultEnum;
import com.somnus.springboot.pay.support.exceptions.PayException;
import com.somnus.springboot.pay.support.exceptions.UnsupportedRequestTypeException;

/**
 * @ClassName:     PaymentChannelHandler.java
 * @Description:   支付渠道处理器
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年12月28日 上午10:21:18
 */
public abstract class PaymentChannelHandler {

    private transient Logger          log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected PayService               payService;

    @Autowired
    protected CallbackService          callbackService;

    @Autowired
    protected PaymentOrderService       paymentOrderService;

    @Autowired
    private PaymentChannelSwitchService paymentChannelSwitchService;

    private final Set<PayChannel>       supportChannels;

    private static ThreadLocal<RequestParameter<?, ?>> parameterHolder = new ThreadLocal<RequestParameter<?, ?>>();

    public PaymentChannelHandler(PayChannel...supportChannel){
        this.supportChannels = new HashSet<PayChannel>(Arrays.asList(supportChannel));
    }

    /**
     * 当前处理器是否支持处理当前回调请求
     *
     * @param parameter 回调请求参数
     * @return 是否支持
     */
    protected <P, R> boolean isSupport(RequestParameter<P, R> parameter){
        return parameter != null && supportChannels.contains(parameter.getChannel());
    }

    protected RequestParameter<?, ?> getRequestParameter(){
        return parameterHolder.get();
    }

    /**
     * 处理支付渠道的回调请求
     * @param parameter 回调参数
     */
    @SuppressWarnings("unchecked")
    public <P, R> R handle(RequestParameter<P, R> parameter){
        log.info("处理[{}]渠道的[{}]请求:[{}]", new Object[]{parameter.getChannel(), parameter.getType(), parameter.getParameter()});
        boolean allow = paymentChannelSwitchService.getValue(parameter.getChannel(), parameter.getType());
        Assert.isTrue(allow, "payment channel service is unable");
        parameterHolder.set(parameter);
        RequestType type = parameter.getType();
        Object result = null;
        switch (type) {
            case RETURN:
            case NOTIFY:
                result = this.handleCallback((RequestParameter<P, String>) parameter);
                break;
            case ORDER:
                result = this.handleOrder((RequestParameter<PaymentOrder, String>) parameter);
                break;
            case QUERY:
                result = this.handleQuery((RequestParameter<String, Map<String, String>>) parameter);
                break;
            case CONFIRM:
                result = this.handleConfirm((RequestParameter<String, PaymentResult>) parameter);
                break;
            default:
                throw new UnsupportedRequestTypeException();
        }
        parameterHolder.remove();
        return (R) result;
    }

    protected String handleCallback(RequestParameter<?, String> parameter) {
        Map<String, String> data = this.parseParam(parameter.getParameter());
        Assert.isNull(data, "parameter must not be null");
        String result = null;
        if(parameter.getType() == RequestType.RETURN){
            result = payService.getReturnUrl(this.handleReturn(data));
        }else if(parameter.getType() == RequestType.NOTIFY){
            result = this.handleNotify(data);
        }else {
            throw new PayException(ResultEnum.ERROR_300001);
        }
        Assert.isNull(result, "result must not be empty");
        try {
            PaymentResult[] paymentResult = this.convert(data);
            List<Callback> callbacks = new ArrayList<Callback>(paymentResult.length);
            int localCount = 0;
            for (int i = 0; i < paymentResult.length; i++) {
                log.info("处理[{}]渠道的[{}]转换成paymentResult:[{}]", new Object[]{parameter.getChannel(), parameter.getType()/*, JSON.toJSONString(paymentResult[i])*/});
                if(PaymentOrderType.SCCUESS.getValue().equals(paymentResult[i].getStatus())){
                    PaymentOrder paymentorder = paymentOrderService.get(paymentResult[i].getOrderId());
                    if(paymentorder == null){
                        log.info("转发订单[{}]的回调通知", paymentResult[i].getOrderId());
                        try {
                            payService.backNotifyInnerAddress(data, parameter.getChannel());
                        } catch (Exception e) {
                            log.warn("转发订单[" + paymentResult[i].getOrderId() + "]的回调通知失败", e);
                        }
                    }else {
                        localCount++;
                        parameter.setChannel((null == paymentResult[i].getChannel())?parameter.getChannel():paymentResult[i].getChannel());
                        paymentResult[i].setChannel(parameter.getChannel());
                        /*CallbackId id = new CallbackId(paymentResult[i].getOrderId(), parameter.getChannel(), parameter.getType());*/
                        callbacks.add(new Callback(/*id, JSON.toJSONString(paymentResult[i])*/));
                    }
                }else {
                    log.info("非支付成功通知,可忽略:{}"/*, JSON.toJSONString(paymentResult[i])*/);
                }
            }
            if(localCount > 0){
                if(log.isInfoEnabled()){
                    log.info("保存支付回调通知记录:{}"/*, JSON.toJSONString(callbacks)*/);
                }
                callbackService.save(callbacks.toArray(new Callback[callbacks.size()]));
                /*SpringContextUtil.getApplicationContext().publishEvent(new CallbackProcessEvent(this, parameter, paymentResult));*/
            }
        } catch (Exception e) {
            throw new PayException(ResultEnum.ERROR_300001);
        }
        return result;
    }

    /**
     * 处理通知类型的回调请求
     *
     * @param data
     * @return 请求响应结果
     */
    protected abstract String handleNotify(Map<String, String> data);

    /**
     * 处理返回类型的回调请求
     *
     * @param data
     * @return 订单号
     */
    protected abstract String handleReturn(Map<String, String> data);

    /**
     * 返回失败时的HTTP响应内容
     *
     * @return 失败时的HTTP响应内容
     */
    public abstract String getFailedResponse(RequestParameter<?, ?> parameter);

    /**
     * 将通知数据转换到标准支付结果
     * @param data 通知数据
     * @return 标准支付结果
     */
    protected abstract PaymentResult[] convert(Map<String, String> data);

    /**
     * 构建第三方支付页面跳转表单
     * @param parameter 请求参数
     * @return 跳转表单
     */
    protected abstract String handleOrder(RequestParameter<PaymentOrder, String> parameter);

    /**
     * 根据订单号查询第三方支付结果
     * @param parameter 请求参数
     * @return 第三方支付结果
     */
    protected abstract Map<String, String> handleQuery(RequestParameter<String, Map<String, String>> parameter);

    /**
     * 根据订单号确认支付是否成功
     * @param parameter 请求参数
     * @return 支付是否成功
     */
    protected PaymentResult handleConfirm(RequestParameter<String, PaymentResult> parameter){
        log.warn("当前渠道不支持主动确认支付结果");
        return parameter.getResult();
    }

    /**
     * 解析回调参数（默认是获取key/Value处理）,子类有特殊的待重写
     * @return
     */
    @SuppressWarnings("unchecked")
    protected Map<String, String> parseParam(Object parameter){
        if(parameter instanceof Map){
            return Map.class.cast(parameter);
        }else if(parameter instanceof CharSequence){
            String text = parameter.toString();
            String[] pairs = text.split("&");
            if(pairs != null && pairs.length > 0){
                log.info("尝试解析key-value结构的requestbody支付参数");
                Map<String, String> params = new HashMap<String, String>();
                for (int i = 0; i < pairs.length; i++) {
                    if(!StringUtils.isEmpty(pairs[i])){
                        String[] item = pairs[i].split("=");
                        String value = "";
                        try {
                            value = item.length > 1 ? URLDecoder.decode(item[1], "UTF-8") : "";
                        } catch (Exception e) {
                            log.warn("URL转码失败", e);
                        }
                        params.put(item[0], value);
                    }
                }
                return params.isEmpty() ? null : params;
            }
        }
        return null;
    }

}
