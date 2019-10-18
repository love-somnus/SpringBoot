package com.somnus.springboot;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author Somnus
 * @packageName com.somnus.springboot
 * @title: HandlerStatusController
 * @description: TODO
 * @date 2019/10/14 17:15
 */
@RestController
public class HandlerStatusController {

    /**
     * 当一个Controller中有多个@ExceptionHandler注解出现时，那么异常被哪个方法捕捉呢？这就存在一个优先级的问题
     * @ExceptionHandler的优先级是：在异常的体系结构中，哪个异常与目标方法抛出的异常血缘关系越紧密，就会被哪个捕捉到
     * @ExceptionHandler这个只会是在当前的Controller里面起作用，如果想在所有的Controller里面统一处理异常的话，可以用@ControllerAdvice来创建一个专门处理的类
     */
    @ExceptionHandler({ ArrayIndexOutOfBoundsException.class })
    @ResponseStatus(value= HttpStatus.BAD_GATEWAY)
    public void handleArrayIndexOutOfBoundsException(Exception e) {
        e.printStackTrace();
    }

    @GetMapping(value = "h/{id}")
    public String testExceptionHandle(@PathVariable(value = "id") Integer id) {
        List<String> list = Arrays.asList(new String[]{"a","b","c","d"});
        return list.get(id);
    }

    @GetMapping(value = "h2/{id}")
    public Integer testExceptionHandle2(@PathVariable(value = "id") String id) {
        return Integer.valueOf(id);
    }
}
