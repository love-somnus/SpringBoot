package com.somnus.springboot;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Somnus
 * @packageName com.somnus.springboot
 * @title: ResponseStatusController
 * @description: TODO
 * @date 2019/10/14 17:00
 */
@RestController
public class ResponseStatusController {

    /**
     * 抛出的异常，没有谁处理（响应界面丑陋，异常栈信息全打印了），并给出错误Status码
     */
    @GetMapping(value = "s/{id}")
    public String status(@PathVariable(value = "id") Integer id){
        if(id % 2 != 0){
            throw new HttpStatusException();
        }
        return id.toString();
    }

    @GetMapping(value = "s2/{id}")
    @ResponseStatus(value=HttpStatus.BAD_GATEWAY)
    public String status2(@PathVariable(value = "id") Integer id){
        System.out.println(10 / id);
        return id.toString();
    }
}

/**
 *  不要轻易把@ResponseStatus修饰目标方法，因为无论它执行方法过程中有没有异常产生，用户都会看到错误Status码
 */
@ResponseStatus(value= HttpStatus.BAD_GATEWAY)
class HttpStatusException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HttpStatusException() {
        super();
    }

    public HttpStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatusException(String message) {
        super(message);
    }

    public HttpStatusException(Throwable cause) {
        super(cause);
    }

}