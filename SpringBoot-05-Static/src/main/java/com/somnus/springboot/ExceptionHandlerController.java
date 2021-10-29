package com.somnus.springboot;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Somnus
 * @packageName com.somnus.springboot
 * @title: ExceptionHandlerController
 * @description: TODO
 * @date 2019/10/14 16:48
 */
@RestController
public class ExceptionHandlerController {

    /**
     * 当访问e/0的时候，会抛出ArithmeticException异常，@ExceptionHandler就会处理并响应error
     */
    @ExceptionHandler({ ArithmeticException.class })
    public String handleArithmeticException(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        System.out.println(request.getRequestURI());
        return "error";
    }

    @GetMapping(value = "e/{id}")
    public String testExceptionHandle(@PathVariable(value = "id") Integer id) {
        System.out.println(10 / id);
        return id.toString();
    }
}
