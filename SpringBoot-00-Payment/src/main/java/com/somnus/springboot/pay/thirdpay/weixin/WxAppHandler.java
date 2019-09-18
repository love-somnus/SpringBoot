package com.somnus.springboot.pay.thirdpay.weixin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.somnus.springboot.pay.enums.PayChannel;
import com.somnus.springboot.pay.pojo.PaymentOrder;
import com.somnus.springboot.pay.pojo.PaymentResult;
import com.somnus.springboot.pay.support.util.SystemUtil;
import com.somnus.springboot.pay.support.util.WebUtil;
import com.somnus.springboot.pay.thirdpay.RequestParameter;
import com.somnus.springboot.pay.thirdpay.weixin.config.WxConfig;
import com.somnus.springboot.pay.thirdpay.weixin.util.PayCommonUtil;
import com.somnus.springboot.pay.thirdpay.weixin.util.WxUtil;
import com.somnus.springboot.pay.thirdpay.weixin.util.XMLUtil;

@Service     
public class WxAppHandler extends AbstractWxHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(WxAppHandler.class);
	
	@Value("${wx.app.back.url}")
	private String notifyAppUrl;
	
	public WxAppHandler() {
		super(PayChannel.WxAppPay, WxConfig.WX_APP_API_KEY, WxConfig.WX_APP_APP_ID, WxConfig.WX_APP_MCH_ID );
	}

	@Override
	public PaymentResult[] convert(Map<String, String> data) {
		PaymentResult[] paymentResult = super.convert(data);
		for (int i = 0; i < paymentResult.length; i++) {
			/*CacheServiceExcutor.remove(Constants.PAY_WX_OTHERAPP_MEMCACHE_KEY_B5M + paymentResult[i].getOrderId());*/
		}
		return paymentResult;
	}

	@Override
	public String handleOrder(RequestParameter<PaymentOrder, String> parameter) {
		Assert.isTrue(null == parameter.getParameter() || StringUtils.isNotEmpty(parameter.getParameter().getOrderId()), "PARAMETER_ERROR");
		HttpServletRequest request = WebUtil.getRequest();
		Map<String,String> propsMap = new HashMap<String,String>();
		propsMap.put(WxConfig.APP_ID, appId);
		propsMap.put(WxConfig.MCH_ID, mchId);
		propsMap.put(WxConfig.MCH_API_KEY, key);
		String requestXML = transRequestWxPara2Xml(parameter.getParameter(), request, propsMap);
		Map<String, String> map = null;
		String html = null;
		try {
			String result = Request.Post(WxConfig.UNIFIED_ORDER_URL).bodyString(requestXML,ContentType.APPLICATION_XML).execute().returnContent().asString();
			map = XMLUtil.doXMLParse(result);
			JSONObject json = new JSONObject();
			String errcode = map.get("err_code");
			String errmsg =  map.get("err_code_des");
			json.put("errcode", errcode);
			json.put("errmsg", errmsg);
			String returnCode = map.get("return_code");
			String resultCode = map.get("result_code");
			String orderId = parameter.getParameter().getOrderId();
			if (returnCode.equalsIgnoreCase("SUCCESS") && resultCode.equalsIgnoreCase("SUCCESS")) {
				String appid = propsMap.get(WxConfig.APP_ID);
				String partnerid =  propsMap.get(WxConfig.MCH_ID);
				String getUnixTime = WxUtil.getUnixTime(new Date()) +"";
				String noncestr = PayCommonUtil.createNoncestr();
				Map<String, String> parameters = new HashMap<String, String>();
				parameters.put("appid", appid);
				parameters.put("partnerid", partnerid);
				parameters.put("prepayid", map.get("prepay_id"));
				parameters.put("package", "Sign=WXPay");
				parameters.put("noncestr",noncestr);
				parameters.put("timestamp",getUnixTime);
				String sign_result =  PayCommonUtil.createSign(parameters, key);
				
				JSONObject json2 = new JSONObject();
				json2.put("appid", appid);
				json2.put("noncestr", noncestr);
				json2.put("package", "Sign=WXPay");
				json2.put("partnerid", partnerid);
				json2.put("prepayid", map.get("prepay_id"));
				json2.put("sign", sign_result);
				json2.put("timestamp", getUnixTime);
				json2.put("success_return", payService.getReturnUrl(parameter.getParameter()));
				json.put("appresult", json2.toJSONString());
				json.put("errcode", "0");
				json.put("errmsg", "Success");
				/*MemCachedUtil.setCache(Constants.PAY_WX_OTHERAPP_MEMCACHE_KEY_B5M + orderId,json.toJSONString());*/
			}else{
				if(WxConfig.OUT_TRADE_NO_USED.equals(errcode)){
					Object wxPayResult = new Object()/*MemCachedUtil.getCache(Constants.PAY_WX_OTHERAPP_MEMCACHE_KEY_B5M + orderId)*/;
					if(null!=wxPayResult){
						if(LOGGER.isWarnEnabled()){
							LOGGER.warn("newWXApp get prepayId in memCache,orderId:"+orderId +",res:"+wxPayResult.toString());
						}
						return wxPayResult.toString();
					}
				}
			}
			html = json.toString();
		} catch (Exception e) {
			LOGGER.warn("解析微信返回结果异常：{}", e);
		}
		return html;
	}
	
	/**
	 * 转换微信需求参数格式XML
	 * @param paymentOrder
	 * @param request
	 * @param propsMap
	 * @return
	 */
	private String transRequestWxPara2Xml(PaymentOrder paymentOrder, HttpServletRequest request, Map<String, String> propsMap){
		LOGGER.info("转换微信需求参数格式XML参数[{}]+[{}]", paymentOrder, propsMap);
		if (null == paymentOrder || null == paymentOrder.getOrderId())
			return null;
		Map<String, String> parameters = new HashMap<String, String>();
		String appid = propsMap.get(WxConfig.APP_ID);
		String partnerid =  propsMap.get(WxConfig.MCH_ID);

		parameters.put("appid", appid);	//公众号
		String body = paymentOrder.getOrderId();
		if (paymentOrder.getIsCombined()) {
			body = paymentOrder.getParentOrderId();
		}
		parameters.put("body", body);
		if(StringUtils.isNotBlank(paymentOrder.getImei())){
			parameters.put("device_info", paymentOrder.getImei());
		}
		parameters.put("mch_id", partnerid);	//商户号
		parameters.put("nonce_str", PayCommonUtil.createNoncestr());	//随机码
		parameters.put("notify_url", WebUtil.getRootPath() + notifyAppUrl);	//支付成功后回调的地址
		String orderId = paymentOrder.getOrderId();	// 商家订单号
		//合并支付的订单
		if (paymentOrder.getIsCombined()) {
			orderId = paymentOrder.getParentOrderId();
		}
		parameters.put("out_trade_no", orderId);	//订单号
		String user_ip = SystemUtil.getRemoteIP(request);
		if(StringUtils.isBlank(user_ip) || user_ip.indexOf("0:0:0:0") != -1){  //测试使用
			user_ip = "116.231.217.212";
		}
		parameters.put("spbill_create_ip",user_ip.trim());	// 用户ip
		parameters.put("time_stamp", "" + WxUtil.getUnixTime(new Date()));	//时间戳（秒级）
		String totalFee = paymentOrder.getAmount().multiply(WxConfig.PAY_MOUNT_UNIT).toString();
		parameters.put("total_fee", totalFee);	// 商品金额,以分为单位
		parameters.put("trade_type", "APP");
		parameters.put("sign",PayCommonUtil.createSign(parameters, key));
		return  PayCommonUtil.getRequestXml(parameters);
	}

}
