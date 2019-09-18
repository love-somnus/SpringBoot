package com.somnus.springboot.pay.enums;

public enum SwitchType {

    PAYMENT_CHANNEL("支付渠道");

    private String desc;

    private SwitchType(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
