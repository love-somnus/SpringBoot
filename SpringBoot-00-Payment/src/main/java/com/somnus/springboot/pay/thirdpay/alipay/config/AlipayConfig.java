package com.somnus.springboot.pay.thirdpay.alipay.config;

/**
 * @ClassName:     AlipayConfig.java
 * @Description:   支付宝配置信息
 * @author         Somnus
 * @version        V1.0  
 * @Date           2017年1月3日 上午11:50:06
 */
public class AlipayConfig {
    /** 合作身份者ID，以2088开头由16位纯数字组成的字符串 */
    public static final String ALI_SELLER_ID = "";
    /** 商户号*/
    public static final String ALI_PARTNER = "";
    /** 商户的私钥*/
    public static final String ALI_KEY = "";
    public static final String ALI_PUBLIC_KEY = ""; // 支付宝的公钥，如果签名方式设置为“0001”时，请设置该参数
    public static final String ALI_PRIVATE_KEY = "";
    public static final String ALI_PRIVATE_KEY_PKCS8 = "";

    /** 字符编码格式 目前支持 gbk 或 utf-8*/
    public static final String INPUT_CHARSET 	= "utf-8";
    /** 签名方式 不需修改*/
    public static final String SIGN_TYPE 		= "MD5";
    /** 支付的单位为元*/
    public static final int    PAY_MOUNT_UNIT 	= 1;
    
    public static final String PAY_SERVICE_NAME = "mobile.securitypay.pay";
    
    /** PC 支付宝回调地址 */
    public static final String WEB_NOTIFY_URL = "";
    public static final String WEB_RETURN_URL = "";
    
    public static final String WEB_REFUND_URL = "";
    /** wap 支付宝回调地址 */
    public static final String WAP_NOTIFY_URL = "";
    public static final String WAP_RETURN_URL = "";
    
    /** APP 支付宝回调地址*/
    public static final String APP_NOTIFY_URL = "";

    /** 支付宝常量*/
    public static final String TRADE_FINISHED        = "TRADE_FINISHED";
    public static final String TRADE_SUCCESS         = "TRADE_SUCCESS";
    public static final String TRADE_WAIT_BUYER_PAY  = "WAIT_BUYER_PAY";
    public static final String TRADE_TRADE_CLOSED    = "TRADE_CLOSED";
    public static final String TRADE_TRADE_PENDING   = "TRADE_PENDING";

    // 支付宝 notify_url 需要 返回的两种值
    public static final String NOTIFY_SUCCESS_RESULT = "success";
    public static final String NOTIFY_FAIL_RESULT    = "fail";

    // 支付宝 支付涉及的参数名
    public static final String OUT_TRADE_NO 	= "out_trade_no";
    public static final String SUBJECT 			= "subject";
    public static final String TRADE_STATUS 	= "trade_status";
    public static final String TRADE_NO 		= "trade_no";
    public static final String TOTAL_FEE 		= "total_fee";
    public static final String BUYER_ID 		= "buyer_id";
    public static final String NOTIFY_ID 		= "notify_id";
    public static final String SIGN_KEY 		= "sign";
    public static final String SIGN_TYPE_RSA 	= "RSA";
    public static final String SIGN_TYPE_MD5 	= "MD5";

    // 支付宝 部分参数默认值
    public static final String PAYMETHOD 			= "bankPay";
    public static final String DOUBLE_FORMAT 		= "0.00";
    public static final String SUCCESS_CODE 	= "100";
    public static final String VERIFY_RESPONSE_TRUE = "true";
    
    public static final String CROSS_PAY_REQUEST_WAP = "https://mapi.alipay.com/gateway.do";
    public static final String CROSS_PAY_CURRENCY = "currency";
    public static final String CROSS_PAY_SERVICE_NAME = "single_trade_query";
    public static final String CROSS_PAY_CURRENCY_TYPE = "USD";
    public static final String CROSS_PAY_SIGN_TYPE = "MD5";

    /** 阿里测试环境 */
    public static String TEST_KEY = ""; // 商户的私钥
    public static String TEST_PARTNER = ""; // 商户的私钥
    public static final String TEST_CROSS_PAY_REQUEST_WAP = "http://openapi.alipaydev.com/gateway.do"; //测试api接口
    
    public static final String WAP_PAY_SERVICE_NAME = "alipay.wap.create.direct.pay.by.user";
    public static final String WAP_PAYMENT_TYPE = "1";
    
}
