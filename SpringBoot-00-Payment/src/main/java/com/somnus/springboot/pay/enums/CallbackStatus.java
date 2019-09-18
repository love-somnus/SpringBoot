package com.somnus.springboot.pay.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:     CallbackStatus.java
 * @Description:   第三方支付渠道回调处理状态
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年12月28日 上午10:29:24
 */
public enum CallbackStatus {

    INIT("初始化"), SUCCESS("成功"), FAILURE("失败");

    private String desc;

    private CallbackStatus(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static List<CallbackStatus> getAll(){
        List<CallbackStatus> list = new ArrayList<CallbackStatus>();
        for (CallbackStatus type : CallbackStatus.values()) {
            list.add(type);
        }
        return list;
    }

    public static CallbackStatus descOf(String desc){
        CallbackStatus callbackStatus = null;
        if (desc != null){
            for (CallbackStatus type : CallbackStatus.values()) {
                if (type.desc.equalsIgnoreCase(desc))
                    callbackStatus = type;
            }
        }
        return callbackStatus;
    }

}
