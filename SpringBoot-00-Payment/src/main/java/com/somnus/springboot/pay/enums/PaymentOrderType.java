package com.somnus.springboot.pay.enums;

/**
 * @ClassName:     PaymentOrderType.java
 * @Description:   订单状态
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年12月28日 上午10:31:59
 */
public enum PaymentOrderType {

    NOTDONE(0,"未支付"),
    SCCUESS(1,"支付成功"),
    FAIL(2,"支付失败"),
    DONE(3,"已完成"),
    PAID_ERROR(4,"已付款"),
    ERROR(7,"支付错误");

    private Integer value;
    private String message;

    private PaymentOrderType(Integer value,String message) {
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
    
    public static PaymentOrderType valueOf(Integer value){
        PaymentOrderType type = null;
        if(value != null){
            for (PaymentOrderType t : PaymentOrderType.values()) {
                if(t.getValue().equals(value)){
                    type = t;
                    break;
                }
            }
        }
        return type;
    }

}
