package com.somnus.springboot.pay.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName:     NotifyChannel.java
 * @Description:   支付成功时通知请求发起方的通知类型
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年12月28日 上午10:30:00
 */
public enum NotifyChannel {

    EMPTY(""), METAQ("mq"), HTTP("http");

    private String desc;

    private NotifyChannel(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }


    public static List<NotifyChannel> getAll(){
        List<NotifyChannel> list = new ArrayList<NotifyChannel>();
        for (NotifyChannel type : NotifyChannel.values()) {
            list.add(type);
        }
        return list;
    }

    public static NotifyChannel descOf(String desc){
        NotifyChannel notifyChannel = null;
        if (desc != null){
            for (NotifyChannel type : NotifyChannel.values()) {
                if (type.desc.equalsIgnoreCase(desc))
                    notifyChannel = type;
            }
        }
        return notifyChannel;
    }

}
