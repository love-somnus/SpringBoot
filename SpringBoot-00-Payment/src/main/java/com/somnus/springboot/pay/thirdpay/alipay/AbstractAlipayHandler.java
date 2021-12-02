package com.somnus.springboot.pay.thirdpay.alipay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.somnus.springboot.pay.enums.PayChannel;
import com.somnus.springboot.pay.enums.PaymentOrderType;
import com.somnus.springboot.pay.pojo.PaymentOrder;
import com.somnus.springboot.pay.pojo.PaymentResult;
import com.somnus.springboot.pay.support.common.Constants;
import com.somnus.springboot.pay.support.common.ResultEnum;
import com.somnus.springboot.pay.support.exceptions.PayException;
import com.somnus.springboot.pay.support.util.HTMLUtil;
import com.somnus.springboot.pay.support.util.WebUtil;
import com.somnus.springboot.pay.thirdpay.PaymentChannelHandler;
import com.somnus.springboot.pay.thirdpay.RequestParameter;
import com.somnus.springboot.pay.thirdpay.alipay.config.AlipayConfig;
import com.somnus.springboot.pay.thirdpay.alipay.sign.MD5;
import com.somnus.springboot.pay.thirdpay.alipay.sign.RSA;
import com.somnus.springboot.pay.thirdpay.alipay.util.AlipayNotify;

/**
 * @ClassName:     AbstractAliHandler.java
 * @Description:   支付宝支付渠道回调抽象处理器
 * @author         Somnus
 * @version        V1.0  
 * @Date           2017年1月3日 上午11:44:10
 */
public abstract class AbstractAlipayHandler extends PaymentChannelHandler {

    private final static Logger    LOGGER = LoggerFactory.getLogger(AbstractAlipayHandler.class);

    protected String partner;
    protected String signKey;
    protected boolean covert2RMB = true;

    public AbstractAlipayHandler(PayChannel channel, String partner, String signKey) {
        this(partner, signKey, channel);
    }

    public AbstractAlipayHandler(String partner, String signKey, PayChannel...channel) {
        super(channel);
        this.partner = partner;
        this.signKey = signKey;
    }

    @Override
    public String handleNotify(Map<String, String> data) {
        LOGGER.info("处理支付宝支付后端返回通知:[{}]", data);
        checkSign(data);
        return AlipayConfig.NOTIFY_SUCCESS_RESULT;
    }

    @Override
    public String handleReturn(Map<String, String> data) {
        LOGGER.info("处理支付宝支付前端返回通知:[{}]", data);
        checkSign(data);
        return data.get(AlipayConfig.OUT_TRADE_NO);
    }

    protected void checkSign(Map<String, String> data) {
        boolean success = AlipayNotify.verifyMD5(data, partner, signKey);
        Assert.isTrue(success, "sign签名验证失败");
    }

    @Override
    public String getFailedResponse(RequestParameter<?, ?> parameter) {
        return AlipayConfig.NOTIFY_FAIL_RESULT;
    }

    @Override
    public PaymentResult[] convert(Map<String, String> data) {
        try {
            int isCombined = 0;
            String orderId = data.get(AlipayConfig.OUT_TRADE_NO);
            if (orderId.indexOf(Constants.COMBINED_ORDER_PREFIX) > -1) {
                isCombined = 1;
            } else {
                isCombined = 0;
            }
            PaymentResult paymentResult = new PaymentResult();
            paymentResult.setIsCombined(isCombined);
            paymentResult.setOrderId(orderId);
            paymentResult.setTradeStatus(data.get(AlipayConfig.TRADE_STATUS));
            paymentResult.setThirdTradeNo(data.get(AlipayConfig.TRADE_NO));
            paymentResult.setPrice(new BigDecimal(StringUtils.defaultIfBlank(data.get("total_fee"), "0")));
            paymentResult.setBuyerId(data.get("buyerId"));
            if(covert2RMB){
                LOGGER.info("通过查询接口转换订单[{}]的RMB金额", orderId);
                Map<String,String> queryMap = new HashMap<String,String>();
                queryMap.put(Constants.PAY_ID_KEY,paymentResult.getOrderId());
                Map<String,String> resMap = queryPaymentOrder(queryMap);
                paymentResult.setPrice(new BigDecimal(StringUtils.defaultIfBlank(resMap.get("price"),"0")));
                paymentResult.setBuyerId(resMap.get("buyerId"));
            }
            paymentResult.setPayInfo(data.get(AlipayConfig.CROSS_PAY_CURRENCY)+","+Double.valueOf(StringUtils.defaultIfBlank(data.get(AlipayConfig.TOTAL_FEE),"0")));
            String tradeStatus = data.get(AlipayConfig.TRADE_STATUS);
            boolean status = false;
            //如果退款功能支付成功为“TRADE_SUCCESS”，如果没有退款功能成功为“TRADE_FINISHED”
            if(AlipayConfig.TRADE_SUCCESS.equals(tradeStatus) || AlipayConfig.TRADE_FINISHED.equals(tradeStatus)){
                status = true;
            }
            paymentResult.setStatus(status? PaymentOrderType.SCCUESS.getValue() : PaymentOrderType.FAIL.getValue());
            return new PaymentResult[]{paymentResult};
        } catch (Exception e) {
            LOGGER.warn("支付宝支付结果通知数据格式转换错误", e);
            throw new PayException(ResultEnum.ERROR_300001);
        }
    }

    public Map<String,String> queryPaymentOrder(Map<String, String> requestParamsMap) {
        return queryPaymentOrder(requestParamsMap,true);
    }

    public Map<String,String> queryPaymentOrder(Map<String, String> requestParamsMap, boolean isMD5SignType) {
        Map<String,String> result = null;
        return result;
    }

    protected String getSign(TreeMap<String, String> param) {
        return getSign(param,true);
    }

    protected String getSign(TreeMap<String, String> param, boolean isMD5SignType) {
        String res = "";
        StringBuilder builder = new StringBuilder();
        if (MapUtils.isNotEmpty(param)) {
            for (Entry<String, String> entry : param.entrySet()) {
                builder.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
            builder.deleteCharAt(builder.length() - 1);
            res = builder.toString();
            LOGGER.info("即将进行签名计算:{}", res);
            if(isMD5SignType){
                res = MD5.sign(res, signKey, AlipayConfig.INPUT_CHARSET);
            }else{
                res = RSA.sign(res, signKey, AlipayConfig.INPUT_CHARSET);
            }
            LOGGER.info("签名计算结果:{}", res);
        }
        return res;
    }

    @Override
    public String handleOrder(RequestParameter<PaymentOrder, String> parameter) {
        LOGGER.info("构建[{}]支付页面跳转form", parameter.getChannel());
        TreeMap<String, String> requestMap = new TreeMap<String, String>();
        requestMap.put("service", AlipayConfig.CROSS_PAY_SERVICE_NAME);
        requestMap.put("partner", partner);
        requestMap.put("_input_charset", AlipayConfig.INPUT_CHARSET);
        String callback = WebUtil.getRootPath() + "/callback/" + parameter.getChannel().name();
        requestMap.put("notify_url", callback + "/notify.htm");// 服务器异步通知页面路径
        requestMap.put("return_url", callback + "/return.htm");// 页面跳转同步通知页面路径
        requestMap.put("out_trade_no", parameter.getParameter().getOrderId());
        requestMap.put("currency", AlipayConfig.CROSS_PAY_CURRENCY_TYPE);
        requestMap.put("merchant_url", Constants.B5M_DOMAIN);
        requestMap.put("subject", parameter.getParameter().getSubject());
        requestMap.put("rmb_fee", parameter.getParameter().getAmount().toString());
        requestMap.put("sign", getSign(requestMap));
        requestMap.put("sign_type", AlipayConfig.CROSS_PAY_SIGN_TYPE);
        String html = HTMLUtil.createSubmitHtml(AlipayConfig.CROSS_PAY_REQUEST_WAP + "?_input_charset=" + AlipayConfig.INPUT_CHARSET, requestMap, null, null);
        LOGGER.info("支付宝支付跳转form:{}", html);
        return html;
    }

    @Override
    protected Map<String, String> handleQuery(
            RequestParameter<String, Map<String, String>> parameter) {
        // TODO Auto-generated method stub
        return null;
    }

}
