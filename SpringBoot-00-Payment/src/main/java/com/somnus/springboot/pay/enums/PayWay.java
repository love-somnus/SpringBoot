package com.somnus.springboot.pay.enums;

/**
 * @ClassName:     PayWay.java
 * @Description:   支付方式(可用支付渠道组合)
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年12月28日 上午10:35:51
 */
public enum  PayWay {

    COMMON_WAY(0, "国内支付"), CROSSPAY_HONGKONG_WAY(1, "境外支付(香港,可以使用全额帮钻,不报关)"),
    CROSSPAY_KOREA_WAY(2, "境外支付(韩国,可以使用全额帮钻,不报关)"),CROSSPAY_KOREA_WAY2(3, "境外支付(韩国,不允许使用帮钻,不报关)"),
    SUPERBZ_WAY(4, "帮钻支付"),COMMON_NO_SUPERBZ_WAY(5, "国内支付(不允许使用帮钻)"),
    CROSSPAY_HONGKONG_WAY2(6, "境外支付(香港,不允许使用帮钻,不报关)"),
    CROSSPAY_KOREA_NINGBO_WAY1(7, "宁波仓1境外支付(香港,不允许使用帮钻,报宁波海关)"),
    CROSSPAY_HONGKONG_NINGBO_WAY2(8, "宁波仓2境外支付(香港,不允许使用帮钻,报宁波海关)"),
    CROSSPAY_B5C_WAY(9, "帮我采境外支付(香港,不报关)"),
    CROSSPAY_HONGKONG_GZ_NS(10, "广州南沙境外支付(香港,不允许使用帮钻,报南沙海关)"),
    CROSSPAY_HONGKONG_GZ_BY(11, "广州白云境外支付(香港,不允许使用帮钻,报广州海关)");

    private Integer value;
    private String message;

    PayWay(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static boolean isNeedCrossPay(Integer crossPay){
        if(null != crossPay && (CROSSPAY_HONGKONG_WAY.getValue().equals(crossPay) || CROSSPAY_KOREA_WAY.getValue().equals(crossPay))){
            return true;
        }
        return false;
    }
}
