package com.somnus.springboot.pay.thirdpay.alipay;

import com.alibaba.fastjson.JSONObject;
import com.somnus.springboot.pay.enums.PayChannel;
import com.somnus.springboot.pay.enums.UAType;
import com.somnus.springboot.pay.pojo.PaymentOrder;
import com.somnus.springboot.pay.support.common.Constants;
import com.somnus.springboot.pay.support.util.WebUtil;
import com.somnus.springboot.pay.thirdpay.RequestParameter;
import com.somnus.springboot.pay.thirdpay.alipay.config.AlipayConfig;
import com.somnus.springboot.pay.thirdpay.alipay.sign.RSA;
import com.somnus.springboot.pay.thirdpay.alipay.util.AlipayNotify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;

import java.net.URLEncoder;
import java.util.Map;

@Service
public class AlipayAppHandler extends AbstractAlipayHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(AlipayAppHandler.class);

    public AlipayAppHandler() {
        super(PayChannel.AliAppPay, AlipayConfig.ALI_PARTNER, AlipayConfig.ALI_KEY);
        this.covert2RMB = false;
    }

    @Override
    public String handleOrder(RequestParameter<PaymentOrder, String> parameter) {
        LOGGER.info("创建支付钱包渠道[{}]支付请求提交表单:[{}]", parameter.getChannel(), parameter.getParameter());
        HttpServletRequest request = WebUtil.getRequest();
        JSONObject json = new JSONObject();
        json.put("code", AlipayConfig.SUCCESS_CODE);
        json.put("success", AlipayConfig.SUCCESS_CODE);
        String body = parameter.getParameter().getOrderId();
        if (parameter.getParameter().getIsCombined()) {
            body = parameter.getParameter().getParentOrderId();
        }
        StringBuilder builder = new StringBuilder();
        builder.append("partner=\"" + partner + "\"");
        builder.append("&seller_id=\"" + AlipayConfig.ALI_SELLER_ID + "\"");
        builder.append("&out_trade_no=\"" + parameter.getParameter().getOrderId() + "\"");
        builder.append("&subject=\"" + parameter.getParameter().getSubject() + "\"");
        builder.append("&body=\"" + body + "\"");
        builder.append("&total_fee=\"" + parameter.getParameter().getAmount().toString() + "\"");
        String callback = WebUtil.getRootPath() + "/callback/" + parameter.getChannel().name();
        builder.append("&notify_url=\"" + callback + "/notify.htm" + "\"");
        builder.append("&service=\"" + AlipayConfig.PAY_SERVICE_NAME + "\"");
        builder.append("&payment_type=\"1\"");
        builder.append("&_input_charset=\"" + AlipayConfig.INPUT_CHARSET + "\"");
        if (UAType.isIOSPlatform(request.getHeader("User-Agent"))) { // 区别 ios和 andriod的参数
            builder.append("&show_url=\"m.alipay.com\"");
        }else {
            builder.append("&return_url=\"m.alipay.com\"");
        }
        String orderInfo = builder.toString();
        String sign =  RSA.sign(orderInfo, AlipayConfig.ALI_PRIVATE_KEY_PKCS8, AlipayConfig.INPUT_CHARSET);
        JSONObject json2 = new JSONObject();
        json2.put("orderInfo", orderInfo);
        try {
            json2.put("sign", URLEncoder.encode(sign,"UTF-8"));
        } catch (Exception e) {
            LOGGER.warn("不支持UTF-8编码",e);
        }
        json2.put("success_return", payService.getReturnUrl(parameter.getParameter()).replace(Constants.NOPAY_STATUS_STR,Constants.PAID_STATUS_STR));
        json.put("appresult", json2.toJSONString());
        return json.toJSONString();
    }

    @Override
    protected void checkSign(Map<String, String> data) {
        boolean success = AlipayNotify.verifyRSA(data, this.partner, AlipayConfig.ALI_PUBLIC_KEY);
        Assert.isTrue(success, "sign签名验证失败");
    }

}
