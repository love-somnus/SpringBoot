package com.somnus.springboot.pay.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PaymentPageChannel implements Serializable {

    private static final long serialVersionUID = 5290492500860227445L;

    private Integer id; // 渠道id
    private Integer isEnabledBz; //  是否支持帮钻支付
    private String bzLimit;//帮钻支付金额（1/1%）
    private Integer isDisabledEnabledBz; //  是否支持帮钻支付
    private Integer isBZModeDisplay; //  是否已帮钻模式显示
    private String bzTipInfo; //  PC帮钻使用描述信息
    private String bzWapTipInfo; //  WAP帮钻使用描述信息
    private String zfPlatformKey; // PC端支付平台区域对应支付渠道父类key
    private List<PaymentChannel> zfPlatformList; // PC端支付支付区域对应支付渠道
    private String payOnlineChannel; // PC端网银支付区域对应主渠道
    private String payOnlinePlatformKey; // PC端网银支付区域对应支付渠道父类key
    private List<PaymentChannel> payOnlinePlatformList; // PC端网银支付区域对应支付渠道
    private String payOnlineZlPlatformKey; // PC端网银支付区域对应银行直连支付渠道父类key
    private List<PaymentChannel> payOnlineZlPlatformList; // PC端网银支付区域对应银行直连支付渠道
    private Integer isNeedBankDirect; // PC端网银支付区域是否开启对应银行直连支付渠道
    private List<PaymentChannel> payOnlineMorePlatformList; // PC端网银支付区域对应银行直连/普通支付渠道
    private String fastPayChannel; // PC端快捷支付区域对应主渠道
    private String fastPayPlatformKey; // PC端快捷支付区域对应支付渠道父类key
    private List<PaymentChannel> fastPayPlatformList; // PC端快捷支付区域对应支付渠道
    private String fastPayZlPlatformKey; // PC端快捷支付区域对应银行直连支付渠道父类key
    private List<PaymentChannel> fastPayZlPlatformList; // PC端快捷支付区域对应银行直连支付渠道
    private Integer isNeedFPBankDirect; // PC端快捷支付区域是否开启对应银行直连支付渠道
    private List<PaymentChannel> fastPayMorePlatformList; // PC端快捷支付区域对应银行直连/普通支付渠道
    private String wapPlatformKey; // 手机端浏览器支付区域对应支付渠道父类key
    private List<PaymentChannel> wapPlatformList; // 手机端浏览器对应支付渠道
    private String wxBrowserPlatformKey; // 手机端微信浏览器支付区域对应支付渠道父类key
    private List<PaymentChannel> wxBrowserPlatformList; // 手机端微信浏览器对应支付渠道
    private String appPlatformKey; // 手机端帮我买APP支付区域对应支付渠道父类key
    private List<PaymentChannel> appPlatformList; // 手机端帮韩品APP对应支付渠道
    private String koreaAppPlatformKey; // 手机端帮韩品APP支付区域对应支付渠道父类key
    private List<PaymentChannel> koreaAppPlatformList; // 手机端帮韩品APP对应支付渠道
    private String payOnlineTips;//网银支付区域提示信息
    private String fastPayTips;//快捷支付区域提示信息
    private String payOnlineDivDataMps;//网银支付区域datamps
    private String fastPayDivDataMps;//快捷支付区域datamps
    private Integer status; // 是否有效
    private String desc;//相关描述
    private Date createTime; // 渠道创建时间
    private Date updateTime; // 最后更新时间

}
