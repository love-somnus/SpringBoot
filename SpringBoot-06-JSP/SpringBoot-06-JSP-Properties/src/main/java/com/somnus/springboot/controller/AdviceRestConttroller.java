package com.somnus.springboot.controller;

import com.google.common.primitives.Chars;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2018/12/6 9:53
 */
@RestController
@RequestMapping("rest")
public class AdviceRestConttroller {

    @GetMapping("calculate")
    public Integer calculate(Integer num){
        System.out.println(1/num);
        return num;
    }

    @GetMapping("array")
    public Integer array(Integer num){
        System.out.println(Chars.asList('a', 'b').get(num));
        return num;
    }

}
