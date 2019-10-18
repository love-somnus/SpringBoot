package com.somnus.springboot;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Somnus
 * @packageName com.somnus.springboot
 * @title: GlobalExceptionHandler
 * @description: TODO
 * @date 2019/10/14 17:50
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ NumberFormatException.class })
    @ResponseStatus(value= HttpStatus.OK)
    @ResponseBody
    public String handleNumberFormatException(Exception e) {
        e.printStackTrace();
        return e.getMessage();
    }
}
