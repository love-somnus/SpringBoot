package com.somnus.springboot.pay.enums;

/**
 * @ClassName:     NotifyType.java
 * @Description:   支付成功时通知请求发起方的通知类型
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年12月28日 上午10:31:29
 */
public enum NotifyType {

    PAY_SUCCESS("支付成功");

    private String desc;

    private NotifyType(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
