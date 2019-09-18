package com.somnus.springboot.pay.thirdpay.weixin;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.somnus.springboot.pay.enums.PayChannel;
import com.somnus.springboot.pay.enums.PaymentOrderType;
import com.somnus.springboot.pay.pojo.PaymentOrder;
import com.somnus.springboot.pay.pojo.PaymentResult;
import com.somnus.springboot.pay.support.common.ResultEnum;
import com.somnus.springboot.pay.support.exceptions.PayException;
import com.somnus.springboot.pay.support.util.SystemUtil;
import com.somnus.springboot.pay.support.util.WebUtil;
import com.somnus.springboot.pay.thirdpay.PaymentChannelHandler;
import com.somnus.springboot.pay.thirdpay.RequestParameter;
import com.somnus.springboot.pay.thirdpay.weixin.config.WxConfig;
import com.somnus.springboot.pay.thirdpay.weixin.util.PayCommonUtil;
import com.somnus.springboot.pay.thirdpay.weixin.util.WxUtil;
import com.somnus.springboot.pay.thirdpay.weixin.util.XMLUtil;

/**
 * @ClassName:     AbstractWxHandler.java
 * @Description:   微信(PC)支付渠道回调抽象处理器
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年12月28日 下午7:11:42
 */
public abstract class AbstractWxHandler extends PaymentChannelHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(AbstractWxHandler.class);
	
	// 微信支付统一接口的回调action
	@Value("${wx.native.back.url}")
	private String notifyUrl;
	
	@Value("${wx.native.wap.back.url}")
	private String notifyWapUrl;
	
	@Value("${wx.app.back.url}")
	private String notifyAppUrl;
	
	protected String key;
	protected String appId;
	protected String mchId;
	
	public AbstractWxHandler(PayChannel channel, String key, String appId, String mchId) {
		super(channel);
		this.key = key;
		this.appId = appId;
		this.mchId = mchId;
	}

	@Override
	public String handleNotify(Map<String, String> data) {
		checkSign(data);
		return WxConfig.NOTIFY_SUCCESS_RESULT;
	}

	@Override
	public String handleReturn(Map<String, String> data) {
		checkSign(data);
		return data.get(WxConfig.ORDER_ID);
	}

	/**
	 * 检查通知参数中的签名是否正确
	 * @param data 通知参数
	 * @return 签名是否正确
	 */
	protected void checkSign(Map<String, String> data) {
		LOGGER.info("校验返回结果签名:{}", data);
		String sign = data.remove("sign");
		String localSign = PayCommonUtil.createSign(data, key);
		Assert.isTrue(localSign.equals(sign), "SIGN_VALIDATE_FAILED");
	}
	
	public Map<String, String> parseParam(Object data) {
		Assert.isNull(data, "PAYMENT_PARAMETER_IS_NULL");
		try {
			return XMLUtil.doXMLParse(data.toString());
		} catch (Exception e) {
			LOGGER.warn("解析微信支付回调参数(XML)失败:{}", data.toString());
			throw new PayException(ResultEnum.ERROR_300001);
		}
	}
	
	@Override
	public String getFailedResponse(RequestParameter<?, ?> requestParameter) {
		return WxConfig.NOTIFY_FAIL_RESULT;
	}

	@Override
	public PaymentResult[] convert(Map<String, String> data) {
		int isCombined = 0; // 是否是合并付款
		try {
			isCombined = Integer.valueOf(data.get(WxConfig.ATTACH));
		} catch (Exception e) {
			isCombined = 0;
		}
        PaymentResult paymentResult = new PaymentResult();
		paymentResult.setIsCombined(isCombined);
		paymentResult.setOrderId(data.get(WxConfig.OUT_TRADE_NO));
		String tradeStatus = data.get(WxConfig.RETURN_CODE).equals(WxConfig.TRADE_SUCCESS) && data.get(WxConfig.RESULT_CODE).equals(WxConfig.TRADE_SUCCESS) ? "SUCCESS" :"FAIL" ;
		paymentResult.setTradeStatus(tradeStatus);
		paymentResult.setThirdTradeNo(data.get(WxConfig.TRADE_NO));
		paymentResult.setPrice(new BigDecimal(data.get(WxConfig.TOTAL_FEE)).divide(WxConfig.PAY_MOUNT_UNIT));
		paymentResult.setBuyerId(data.get(WxConfig.BUYER_ID));
		paymentResult.setPayInfo(data.get(WxConfig.PAY_INFO));
		paymentResult.setStatus(WxConfig.TRADE_SUCCESS.equals(paymentResult.getTradeStatus()) ? PaymentOrderType.SCCUESS.getValue() : PaymentOrderType.FAIL.getValue());
		return new PaymentResult[]{paymentResult};
	}

	/**
	 * 转换微信需求参数格式XML
	 * @param paymentOrder
	 * @param request
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	protected String transRequestWxPara2Xml(PaymentOrder paymentOrder,HttpServletRequest request,boolean isWap){
        LOGGER.info("转换微信需求参数格式XML参数[{}]+[{}]", paymentOrder, isWap);
		 if (null == paymentOrder || null == paymentOrder.getOrderId())
	            return null;
        SortedMap<String, String> parameters = new TreeMap<String, String>();         
        parameters.put("appid", appId);	//公众号
        String body = paymentOrder.getOrderId();
        if (paymentOrder.getIsCombined()) {
            body = paymentOrder.getParentOrderId();
        }
        parameters.put("body", body);   
        parameters.put("device_info", "WEB");  
       
        parameters.put("mch_id", mchId);	//商户号
        parameters.put("nonce_str", PayCommonUtil.createNoncestr());	//随机码
        String notify_Url = isWap ? notifyUrl:notifyWapUrl;
        parameters.put("notify_url", WebUtil.getRootPath(request) + notify_Url);	//支付成功后回调的地址
        String  orderId = paymentOrder.getOrderId();	// 商家订单号
        //合并支付的订单
        if (paymentOrder.getIsCombined()) {
        	orderId = paymentOrder.getParentOrderId();
        }        
        parameters.put("out_trade_no", orderId);	//订单号
        String user_ip = SystemUtil.getRemoteIP(request);
        if(StringUtils.isBlank(user_ip) || user_ip.indexOf("0:0:0:0") != -1){  //测试使用
        	user_ip = "116.231.217.212";
        }
        // 使用原生端支付时候获取本机真实IP
        if(!isWap){
            String local_ip = SystemUtil.getLocalIp();
    		if(StringUtils.isBlank(local_ip)){
    			local_ip = "58.220.17.8";
    		}
        	LOGGER.info("user_ip:{} local_ip:{}",user_ip,local_ip);
        	user_ip = "58.220.17.8";
		} else {
			LOGGER.info("wx  WAP APP-- pay  get user_ip:{}", user_ip);
		}
        parameters.put("spbill_create_ip",user_ip.trim());	// 用户ip
        parameters.put("time_stamp", "" + WxUtil.getUnixTime(new Date()));	//时间戳（秒级）    
        String totalFee = paymentOrder.getAmount().multiply(WxConfig.PAY_MOUNT_UNIT).toString();
        parameters.put("total_fee", totalFee);	// 商品金额,以分为单位      
        if(isWap){
        	parameters.put("trade_type", "JSAPI");
        	boolean isRequestOpenid = true ;          	
        	String cookie_code = (String) request.getSession().getAttribute(WxConfig.COOKIE_WX_CODE);
        	String cookie_openid = (String) request.getSession().getAttribute(WxConfig.COOKIE_WX_OPENID);
        	if(StringUtils.isNotBlank(cookie_code) && StringUtils.isNotBlank(cookie_openid)){
    			String []codes = new String[]{"",""};
    			codes = cookie_code.split("_");
    			if(StringUtils.isNotBlank(codes[1]) && paymentOrder !=null && StringUtils.isNotBlank(paymentOrder.getCode())){
    				long timestamp = Long.parseLong(codes[1]);
    				if(System.currentTimeMillis() < timestamp){
    					isRequestOpenid = false ;
    					parameters.put("openid", cookie_openid);
    				}	
    			}  
    		}
    		if(isRequestOpenid){
    			request.getSession().removeAttribute(WxConfig.COOKIE_WX_CODE);
				String access_token_result = "";
				try {
					access_token_result = Request.Post(WxConfig.OAUTH2_URL).bodyForm(Form.form()
							.add("appid", appId)
							.add("secret", WxConfig.APP_SECRET)
							.add("code", paymentOrder.getCode())
							.add("grant_type", "authorization_code")
							.build())
							.execute().returnContent().asString();
				} catch (IOException e) {
					LOGGER.warn("微信oauth2授权失败:{}", WxConfig.OAUTH2_URL);
					throw new PayException(ResultEnum.ERROR_300001);
				}
	     		JSONObject json = JSONObject.parseObject(access_token_result);
				if (!json.isEmpty()) {
					if (StringUtils.isNotBlank(json.getString("openid"))) {
						parameters.put("openid", json.getString("openid"));
						request.getSession().setAttribute(WxConfig.COOKIE_WX_OPENID, json.getString("openid"));
					}
				} 
			}
        }else{
        	parameters.put("trade_type", "NATIVE");
        }
        parameters.put("sign", PayCommonUtil.createSign(parameters, key));         
        return  PayCommonUtil.getRequestXml(parameters);  	
	}

	@Override
	protected Map<String, String> handleQuery(RequestParameter<String, Map<String, String>> parameter) {
		String orderId = parameter.getParameter();
        LOGGER.info("查询订单[{}]支付结果", orderId);
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("appid", appId); // 公众号
        parameters.put("mch_id", mchId); // 商户号
        parameters.put(WxConfig.OUT_TRADE_NO, orderId); //订单号
        parameters.put("nonce_str", PayCommonUtil.createNoncestr()); // 随机码
        parameters.put("sign", PayCommonUtil.createSign(parameters, key));
        LOGGER.info("查询订单支付结果:{}", parameters);
        try {
			String requestXML = PayCommonUtil.getRequestXml(parameters);
			LOGGER.info("查询订单支付结果请求参数:{}", requestXML);
            String response = Request.Post(WxConfig.CHECK_ORDER_URL).bodyString(requestXML,ContentType.APPLICATION_XML).execute().returnContent().asString();
            LOGGER.info("查询订单支付结果返回:{}", response);
            Map<String, String> result = XMLUtil.doXMLParse(response);
            LOGGER.info("响应内容解析结果:{}", result);
            if("SUCCESS".equalsIgnoreCase(result.get("return_code"))){
            	checkSign(result);
            }
            parameter.setResult(result);
            return result;
        }catch (Exception e) {
        	LOGGER.warn("查询微信订单支付结果失败:{}", JSON.toJSONString(parameters));
        	throw new PayException(ResultEnum.ERROR_300001);
        }
	}

	@Override
	protected PaymentResult handleConfirm(RequestParameter<String, PaymentResult> parameter) {
		LOGGER.info("正在确认订单[{}]是否支付成功", parameter.getParameter());
		RequestParameter<String, Map<String, String>> newParameter = new RequestParameter<String, Map<String, String>>();
		newParameter.setChannel(parameter.getChannel());
		newParameter.setType(parameter.getType());
		newParameter.setParameter(parameter.getParameter());
		Map<String, String> result = this.handleQuery(newParameter);
		String returnCode = result.get("return_code");
        String resultCode = result.get("result_code");
        boolean success = "SUCCESS".equalsIgnoreCase(returnCode);
        if(!success){
        	if(StringUtils.isNotEmpty(result.get("return_msg"))){
        		throw new PayException(resultCode+"|"+result.get("return_msg"));
        	}
        }
        success = success && "SUCCESS".equalsIgnoreCase(resultCode);
        BigDecimal amount = result.containsKey("total_fee") ? new BigDecimal(result.get("total_fee")) : BigDecimal.ZERO;
        success = success && amount.compareTo(BigDecimal.ZERO)>0;
        LOGGER.info("订单[{}]支付{},已支付金额:[{}]", new Object[]{parameter.getParameter(), success ? "成功" : "失败", amount});
        PaymentResult paymentResult = new PaymentResult();
        paymentResult.setPay(success);
        if(success){
        	PaymentOrder order = paymentOrderService.get(parameter.getParameter());
        	if(order == null){
        		LOGGER.warn("订单[{}]不存在", parameter.getParameter());
        	}else {
        		BigDecimal toPay = order.getAmount().multiply(WxConfig.PAY_MOUNT_UNIT);
        		LOGGER.info("确认订单[{}]的已支付金额与待支付金额[{}]是否一致", parameter.getParameter(), toPay);
        		success = toPay.compareTo(amount)==0;
			}
        	
        	paymentResult.setThirdTradeNo(result.get(WxConfig.TRADE_NO));
        	parameter.setResult(paymentResult);
        }
        return paymentResult;
	}

}
