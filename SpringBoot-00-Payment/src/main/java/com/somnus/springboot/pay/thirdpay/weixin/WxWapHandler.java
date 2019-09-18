package com.somnus.springboot.pay.thirdpay.weixin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.somnus.springboot.pay.enums.PayChannel;
import com.somnus.springboot.pay.pojo.PaymentOrder;
import com.somnus.springboot.pay.pojo.PaymentResult;
import com.somnus.springboot.pay.support.util.WebUtil;
import com.somnus.springboot.pay.thirdpay.RequestParameter;
import com.somnus.springboot.pay.thirdpay.weixin.config.WxConfig;
import com.somnus.springboot.pay.thirdpay.weixin.util.PayCommonUtil;
import com.somnus.springboot.pay.thirdpay.weixin.util.WxUtil;
import com.somnus.springboot.pay.thirdpay.weixin.util.XMLUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service     
public class WxWapHandler extends AbstractWxHandler {

	public WxWapHandler() {
		super(PayChannel.WxWapPay, WxConfig.WX_NATIVE_API_KEY, WxConfig.WX_NATIVE_APP_ID, WxConfig.WX_NATIVE_MCH_ID );
	}

	@Override
	public PaymentResult[] convert(Map<String, String> data) {
		PaymentResult[] paymentResult = super.convert(data);
		for (int i = 0; i < paymentResult.length; i++) {
			/*CacheServiceExcutor.remove(Constants.PAY_WX_OTHERAPP_MEMCACHE_KEY + paymentResult[i].getOrderId());*/
		}
		return paymentResult;
	}

	@Override
	public String handleOrder(RequestParameter<PaymentOrder, String> parameter) {
		log.info("创建微信wap端jsapi的请求参数:[{}]",parameter.getParameter());
		if (null == parameter.getParameter() || null == parameter.getParameter().getOrderId())
            return null;
		HttpServletRequest request = WebUtil.getRequest();		    
		String requestXML = transRequestWxPara2Xml(parameter.getParameter(), request, true);
        Map<String, String> map = null;
		try {
			String result = Request.Post(WxConfig.UNIFIED_ORDER_URL).bodyString(requestXML,ContentType.APPLICATION_XML).execute().returnContent().asString();
			map = XMLUtil.doXMLParse(result);
			String returnCode = map.get("return_code");
			String resultCode = map.get("result_code");
			JSONObject json = new JSONObject();
			json.put("return_code", returnCode);
			json.put("result_code", resultCode);
			json.put("return_msg", map.get("return_msg"));
			json.put("err_code", map.get("err_code"));
			json.put("err_code_des", map.get("err_code_des"));
			json.put("ua", request.getHeader("User-Agent"));
			json.put("success_return", payService.getReturnUrl(parameter.getParameter()));
			if (returnCode.equalsIgnoreCase("SUCCESS") && resultCode.equalsIgnoreCase("SUCCESS")) {
				Map<String, String> parameters = new HashMap<String, String>();  
				String getUnixTime = WxUtil.getUnixTime(new Date()) +"";
				String nonce_str = PayCommonUtil.createNoncestr() ; 
				parameters.put("appId", appId);
				parameters.put("nonceStr", nonce_str);
				parameters.put("package", "prepay_id=" + map.get("prepay_id"));
				parameters.put("signType", WxConfig.SIGN_TYPE);
				parameters.put("timeStamp",getUnixTime);
				
				json.put("appId", appId);
				json.put("nonce_str", nonce_str);
				json.put("prepay_id",  map.get("prepay_id"));
				json.put("signType", WxConfig.SIGN_TYPE);
				json.put("timeStamp", getUnixTime);
				json.put("paySign", PayCommonUtil.createSign(parameters, key));	
			}
			return json.toJSONString();
		} catch (Exception e) {
			log.error("创建微信wap端jsapi的请求参数失败", e);
		}
		return null;
	}
	
}
