package com.somnus.springboot.pay.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:     PayChannel.java
 * @Description:   支付渠道枚举
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年12月28日 上午10:37:42
 */
public enum PayChannel {
    AliAppPay(11, "支付宝"), AliWapPay(12, "支付宝(WAP)"),AliHKWapPay(13, "支付宝国际(香港)"),

    BocomPay(21, "交行(网银)"), BocomWapPay(22, "交行(WAP网银)"),

    LianLianPay(31, "连连"), LianLianWapPay(32, "连连(WAP)"),

    WxPay(41, "微信"), WxWapPay(42, "微信(WAP)"), WxAppPay(43, "微信(APP)"),

    UnionPay(51, "银联"), UnionWapPay(52, "银联(WAP)"),

    CCBPay(61, "建行(网银)"), CCBWapPay(62, "建行(WAP网银)"),

    ICBCPay(71, "工行(网银)"),ICBCWapPay(72, "工行(WAP网银)"),

    CMBPay(81, "招行(网银)"), CMBWapPay(82, "招行(WAP网银)"),

    ABCPay(91, "农行(网银)"), ABCWapPay(92, "农行(WAP网银)");


    private Integer value;
    private String desc;
    private PayChannel realPayChannel;

    private PayChannel(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
        this.realPayChannel=this;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static List<PayChannel> getAll(){
        List<PayChannel> list = new ArrayList<PayChannel>();
        for (PayChannel type : PayChannel.values()) {
            list.add(type);
        }
        return list;
    }

    public static PayChannel descOf(String desc){
        PayChannel payType = null;
        if (desc != null){
            for (PayChannel type : PayChannel.values()) {
                if (type.desc.equalsIgnoreCase(desc))
                    payType = type;
            }
        }
        return payType;
    }

    public static PayChannel nameOf(String name){
        PayChannel payType = null;
        if (name != null){
            for (PayChannel type : PayChannel.values()) {
                if (type.name().equalsIgnoreCase(name))
                    payType = type;
            }
        }
        return payType;
    }

    public static PayChannel getPayType(Integer value) {
        PayChannel payType = null;
        if (value == null)
            payType = PayChannel.AliAppPay;
        for (PayChannel pt : PayChannel.values()) {
            if (pt.getValue().equals(value))
                payType = pt;
        }
        return payType;
    }

    public static PayChannel valueOf(Integer value) {
        if (value != null) {
            for (PayChannel payType : PayChannel.values()) {
                if (payType.getValue().equals(value))
                    return payType;
            }
        }
        return null;
    }

    /**
     * 判断是否手机端支付并且是使用sdk方式的支付
     *
     * @param payType
     * @return
     */
    public static boolean isMobileSDKPayWay(PayChannel payType) {
        payType = payType.getRealPayChannel();
        if (PayChannel.WxWapPay == payType || PayChannel.WxAppPay == payType) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.name() + "( " + this.value + " , " + this.desc + " )";
    }

    public PayChannel getRealPayChannel(){
        return this.realPayChannel == null ? this : this.realPayChannel;
    }

}