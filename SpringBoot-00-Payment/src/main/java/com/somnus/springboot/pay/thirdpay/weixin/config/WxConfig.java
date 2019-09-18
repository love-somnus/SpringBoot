package com.somnus.springboot.pay.thirdpay.weixin.config;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

/**
 * @ClassName:     WxConfig.java
 * @Description:   微信支付基础配置类
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年12月28日 下午7:18:07
 */
@Component
public class WxConfig {
	
	/** 微信PCNative */
	/** 商户号*/
	public static final String WX_NATIVE_MCH_ID = "";
	/** 服务号的应用号*/
	public static final String WX_NATIVE_APP_ID= "";
	/** API密钥*/
	public static final String WX_NATIVE_API_KEY = "";

	/**  微信APP */
	/** 商户号*/
	public static final String WX_APP_MCH_ID = "";
	/** 服务号的应用号*/
	public static final String WX_APP_APP_ID= "";
	/** API密钥*/
	public static final String WX_APP_API_KEY = "";
	
	/** 应用密钥*/
	public static final String APP_SECRET = "";
	
	/** appId*/
	public final static String APP_ID = "app_id";
	
	/** 商户号*/
	public final static String MCH_ID = "mch_id";
	
	/** 商户api密钥*/
	public final static String MCH_API_KEY = "mch_api_key";
	
	/** 服务号的配置token*/
	public final static String TOKEN = "weixinCourse";
	
	/** 签名加密方式*/
	public final static String SIGN_TYPE = "MD5";

	/** 支付的单位为分*/
	public static final BigDecimal PAY_MOUNT_UNIT = new BigDecimal("100");
	
	/** 字符编码格式 目前支持 gbk 或 utf-8*/
	public static final String INPUT_CHARSET = "UTF-8";
	
	public static final String COOKIE_WX_CODE = "wxcode";
	
	public static final String COOKIE_WX_OPENID = "wxopenid";

	/** 接口版本*/
	public static String VERSION_TYPE= "3.0";
	
	/** 微信 notify_url 需要 返回的两种值*/
	public static final String NOTIFY_SUCCESS_RESULT = "SUCCESS";
	
	/** */
	public static final String NOTIFY_FAIL_RESULT = "FAIL";
	
	/** 微信支付交易成功的状态*/
	public static final String TRADE_SUCCESS = "SUCCESS";
	
	/** APP支付交易成功的状态*/
	public static final String TRADE_SUCCESS_APP = "0";
	
	/** 返回状态：SUCCESS成功 | FAIL失败*/
	public static final String RETURN_CODE = "return_code";
	
	/** 返回状态：0成功 | 其他失败*/
	public static final String RETURN_CODE_APP = "trade_state";
	
	/** 业务结果*/
	public static final String RESULT_CODE = "result_code";
	
	/** 商户系统的订单号，与请求一致,我们后台自己创建的订单号*/
	public static final String OUT_TRADE_NO = "out_trade_no";
	
	/** 请求参数中支付号的对应key*/
    public static final String ORDER_ID = "orderId";
    
    /** 微信支付交易号*/
	public static final String TRADE_NO = "transaction_id";

	/** 支付金额，单位为分，如果discount有值通知的total_fee+discount = 请求的total_fee*/
	public static final String TOTAL_FEE = "total_fee";
	
	/** 支付结果信息，支付成功时为空*/
	public static final String PAY_INFO = "return_code";
	
	/** 商户号*/
	public static final String PARTNER = "mch_id";
	
	/** 银行类型*/
	public static final String BANK_TYPE = "bank_type";
	
	/** */
	public static final String SUBJECT = "body";
	
	/** */
	public static final String BUYER_ID = "buyer_alias";
	
	public static final String ATTACH = "attach";
	
	/** 商户订单号重复错误码*/
    public static final String OUT_TRADE_NO_USED = "OUT_TRADE_NO_USED";

	public final static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
	
	/** oauth2授权接口(GET)*/
	public final static String OAUTH2_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	
	/** 到用户授权页面获取code*/
	public final static String CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
	
	/** 刷新access_token接口（GET）*/
	public final static String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
	
	/** 菜单创建接口（POST）*/
	public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	/** 菜单查询（GET）*/
	public final static String MENU_GET_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	
	/** 菜单删除（GET）*/
	public final static String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	/** 微信支付统一接口(POST)*/
	public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
	/** 微信退款接口(POST)*/
	public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	
	/** 订单查询接口(POST)*/
	public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	
	/** 关闭订单接口(POST)*/
	public final static String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	
	/** 退款查询接口(POST)*/
	public final static String CHECK_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	
	/** 对账单接口(POST)*/
	public final static String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
	
	/** 短链接转换接口(POST)*/
	public final static String SHORT_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
	
	/** 接口调用上报接口(POST)*/
	public final static String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";
	
	/**
	 * 微信APP使用接口
	 */
	public final static String APP_ORDER_URL = "https://api.weixin.qq.com/pay/genprepay";
	
	/** APP订单查询接口*/
	public final static String APP_CHECK_ORDER_URL = "https://api.weixin.qq.com/pay/orderquery";

}