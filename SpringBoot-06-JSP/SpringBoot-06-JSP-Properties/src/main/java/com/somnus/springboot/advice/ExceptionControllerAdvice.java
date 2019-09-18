package com.somnus.springboot.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2018/12/6 9:49
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = ArithmeticException.class)
    @ResponseBody
    public String ArithmeticException(Exception e) {
        e.printStackTrace();
        return "ArithmeticException";
    }

}