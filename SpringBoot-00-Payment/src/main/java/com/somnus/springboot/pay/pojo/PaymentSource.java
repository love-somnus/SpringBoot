package com.somnus.springboot.pay.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
/*@Table(name = "t_user_payment_source")*/
public class PaymentSource implements Serializable {

    private static final long serialVersionUID = 4990594234839942433L;
    private Integer           id;
    private Integer           sourceId;
    private String            sourceKey;
    private String            returnUrl;
    private String            notifyUrl;
    private String            memo;
    private Date              createTime;

    private String resultUrl;

}
