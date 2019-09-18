package com.somnus.springboot.pay.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean support;

    private PaymentResult result;

}
