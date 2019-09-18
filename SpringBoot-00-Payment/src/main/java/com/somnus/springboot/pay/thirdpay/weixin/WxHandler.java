package com.somnus.springboot.pay.thirdpay.weixin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.somnus.springboot.pay.enums.PayChannel;
import com.somnus.springboot.pay.pojo.PaymentOrder;
import com.somnus.springboot.pay.support.util.WebUtil;
import com.somnus.springboot.pay.thirdpay.RequestParameter;
import com.somnus.springboot.pay.thirdpay.weixin.config.WxConfig;
import com.somnus.springboot.pay.thirdpay.weixin.util.XMLUtil;

@Service     
public class WxHandler extends AbstractWxHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(WxHandler.class);
	
	public WxHandler() {
		super(PayChannel.WxPay, WxConfig.WX_NATIVE_API_KEY, WxConfig.WX_NATIVE_APP_ID, WxConfig.WX_NATIVE_MCH_ID);
	}

	@Override
	public String handleOrder(RequestParameter<PaymentOrder, String> parameter) {
		if(LOGGER.isInfoEnabled()){
            LOGGER.info("创建微信PC端二维码支付的请求参数:[{}]", parameter.getParameter());
        }
        HttpServletRequest request = WebUtil.getRequest();
        String requestXML = transRequestWxPara2Xml(parameter.getParameter(),request, false);
        Map<String, String> map = null;
		try {
			String result = Request.Post(WxConfig.UNIFIED_ORDER_URL).bodyString(requestXML,ContentType.APPLICATION_XML).execute().returnContent().asString();
			map = XMLUtil.doXMLParse(result);
			String returnCode = map.get("return_code");
			String resultCode = map.get("result_code");
			if (returnCode.equalsIgnoreCase("SUCCESS") && resultCode.equalsIgnoreCase("SUCCESS")) {
				return Request.Post(WebUtil.getRootPath() + "/third/wxcode.htm").bodyForm(Form.form()
						.add("finalAmount", parameter.getParameter().getAmount().toString())
						.add("orderId", parameter.getParameter().getOrderId())
						.build())
						.execute().returnContent().asString();
			}else if(returnCode.equalsIgnoreCase("SUCCESS") && !resultCode.equalsIgnoreCase("SUCCESS")){
				return returnCode + "" + map.get("err_code_des") ;
			}
			return map.get("return_code") + map.get("return_msg");
		} catch (Exception e) {
			LOGGER.warn("创建微信PC端二维码支付的请求参数失败", e);
		}
		return null;
	}

}
