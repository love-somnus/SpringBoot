package com.somnus.springboot.pay.pojo;

import java.io.Serializable;
import java.util.Date;

import com.somnus.springboot.pay.enums.CallbackStatus;
import com.somnus.springboot.pay.enums.PayChannel;
import com.somnus.springboot.pay.thirdpay.RequestType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
/*@Table(name = "t_payment_callback")*/
public class Callback implements Serializable {

    private static final long serialVersionUID = 1L;

    private CallbackId id;

    private String data;

    private CallbackStatus status;

    private Date createTime;

    private Date updateTime;

    private String memo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CallbackId implements Serializable {

        private static final long serialVersionUID = 1L;

        private String orderId;

        private PayChannel channel;

        private RequestType type;

        @Override
        public int hashCode() {
            return this.orderId.hashCode() + this.channel.hashCode() + this.type.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof CallbackId){
                CallbackId id = (CallbackId) obj;
                return this.channel == id.getChannel() && this.type == id.getType() && this.orderId.equals(id.getOrderId());
            }
            return false ;
        }

    }
}

