package com.somnus.springboot.pay.support.exceptions;

public class UnsupportedRequestTypeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UnsupportedRequestTypeException() {
        super();
    }

    public UnsupportedRequestTypeException(String message) {
        super(message);
    }

    public UnsupportedRequestTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedRequestTypeException(Throwable cause) {
        super(cause);
    }
    
}
