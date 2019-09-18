package com.somnus.springboot.advice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2018-12-06 11:11:11
 */
@RestControllerAdvice
public class ExceptionRestControllerAdvice {

    @ExceptionHandler(value = IndexOutOfBoundsException.class)
    public String IndexOutOfBoundsException(Exception e) {
        e.printStackTrace();
        return "IndexOutOfBoundsException";
    }

}
