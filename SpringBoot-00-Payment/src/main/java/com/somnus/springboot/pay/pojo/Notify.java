package com.somnus.springboot.pay.pojo;

import java.io.Serializable;
import java.util.Date;

import com.somnus.springboot.pay.enums.NotifyChannel;
import com.somnus.springboot.pay.enums.NotifyStatus;
import com.somnus.springboot.pay.enums.NotifyType;

import lombok.Data;

@Data
/*@Table(name = "t_payment_notify")*/
public class Notify implements Serializable {

    private static final long serialVersionUID = 1L;

    private NotifyId id;

    private int source;

    private NotifyChannel channel = NotifyChannel.EMPTY;

    private String target;

    private String data;

    private NotifyStatus status;

    private Date createTime;

    private Date updateTime;

    private String memo;

    public static class NotifyId implements Serializable {

        private static final long serialVersionUID = 1L;

        private String orderId;

        private NotifyType type;

        public NotifyId(){}

        public NotifyId(String orderId, NotifyType type){
            this.orderId = orderId;
            this.type = type;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public NotifyType getType() {
            return type;
        }

        public void setType(NotifyType type) {
            this.type = type;
        }

        @Override
        public int hashCode() {
            return this.orderId.hashCode() + this.type.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof NotifyId){
                NotifyId id = (NotifyId) obj;
                return this.type == id.getType() && this.orderId.equals(id.getOrderId());
            }
            return false ;
        }

        public String toString(){
            return "orderId=" + this.orderId + ", type=" + this.type;
        }
    }
}

