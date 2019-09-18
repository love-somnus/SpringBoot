package com.somnus.springboot.pay.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:     NotifyStatus.java
 * @Description:   支付成功时通知请求发起方的处理状态
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年12月28日 上午10:31:02
 */
public enum NotifyStatus {

    SUCCESS("成功"), FAILURE("失败");

    private String desc;

    private NotifyStatus(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static List<NotifyStatus> getAll(){
        List<NotifyStatus> list = new ArrayList<NotifyStatus>();
        for (NotifyStatus type : NotifyStatus.values()) {
            list.add(type);
        }
        return list;
    }

    public static NotifyStatus descOf(String desc){
        NotifyStatus notifyStatus = null;
        if (desc != null){
            for (NotifyStatus type : NotifyStatus.values()) {
                if (type.desc.equalsIgnoreCase(desc))
                    notifyStatus = type;
            }
        }
        return notifyStatus;
    }
}
