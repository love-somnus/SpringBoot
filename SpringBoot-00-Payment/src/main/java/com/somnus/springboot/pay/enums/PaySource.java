package com.somnus.springboot.pay.enums;

/**
 * @ClassName:     PaySource.class
 * @Description:   支付系统请求来源
 * @author         Somnus
 * @version        V1.0  
 * @Date           2016年12月28日 上午10:33:49
 */
public enum PaySource {

    ORDER_PAY(223,"PC端支付"),
    MOBILE_PAY(224,"手机端支付");

    private Integer value;
    private String desc;

    PaySource(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static PaySource valueOf(Integer value) {
        if (value != null){
            for (PaySource paymentSource : PaySource.values()) {
                if (paymentSource.getValue().equals(value))
                    return paymentSource;
            }
        }
        return null;
    }

}
