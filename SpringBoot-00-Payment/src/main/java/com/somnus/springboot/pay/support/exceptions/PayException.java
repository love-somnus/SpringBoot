package com.somnus.springboot.pay.support.exceptions;

import com.somnus.springboot.pay.support.common.ResultEnum;

public class PayException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PayException() {
        super();
    }

    public PayException(String message, Throwable cause) {
        super(message, cause);
    }

    public PayException(String message) {
        super(message);
    }

    public PayException(Throwable cause) {
        super(cause);
    }
    
    public PayException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
    }
}
