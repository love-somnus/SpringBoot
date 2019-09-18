package com.somnus.springboot.pay.thirdpay.alipay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.somnus.springboot.pay.enums.PayChannel;
import com.somnus.springboot.pay.pojo.PaymentOrder;
import com.somnus.springboot.pay.support.common.Constants;
import com.somnus.springboot.pay.support.util.HTMLUtil;
import com.somnus.springboot.pay.support.util.WebUtil;
import com.somnus.springboot.pay.thirdpay.RequestParameter;
import com.somnus.springboot.pay.thirdpay.alipay.config.AlipayConfig;

import java.util.TreeMap;

@Service
public class AlipayWapHandler extends AbstractAlipayHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(AlipayWapHandler.class);

    public AlipayWapHandler() {
        super(PayChannel.AliWapPay, AlipayConfig.ALI_PARTNER, AlipayConfig.ALI_KEY);
        this.covert2RMB = false;
    }

    @Override
    public String handleOrder(RequestParameter<PaymentOrder, String> parameter) {
        LOGGER.info("构建支付宝wap去[{}]支付页面跳转form", parameter.getChannel());
        TreeMap<String, String> requestMap = new TreeMap<String, String>();
        requestMap.put("service", AlipayConfig.WAP_PAY_SERVICE_NAME);
        requestMap.put("partner", partner);
        requestMap.put("_input_charset", AlipayConfig.INPUT_CHARSET);
        String callback = WebUtil.getRootPath() + "/callback/" + parameter.getChannel().name();
        requestMap.put("notify_url", callback + "/notify.htm");// 服务器异步通知页面路径
        requestMap.put("return_url", callback + "/return.htm");// 页面跳转同步通知页面路径
        requestMap.put("out_trade_no", parameter.getParameter().getOrderId());
        requestMap.put("merchant_url", Constants.B5M_MOBILE_DOMAIN);
        requestMap.put("subject", parameter.getParameter().getSubject());
        requestMap.put("total_fee", parameter.getParameter().getAmount().toString());
        requestMap.put("seller_id", AlipayConfig.ALI_SELLER_ID);
        requestMap.put("payment_type", AlipayConfig.WAP_PAYMENT_TYPE);
        requestMap.put("sign", getSign(requestMap));
        requestMap.put("sign_type", AlipayConfig.CROSS_PAY_SIGN_TYPE);
        String html = HTMLUtil.createSubmitHtml(AlipayConfig.CROSS_PAY_REQUEST_WAP + "?_input_charset=" + AlipayConfig.INPUT_CHARSET, requestMap, null, null);
        LOGGER.info("支付宝支付跳转form:{}", html);
        return html;
    }

}
