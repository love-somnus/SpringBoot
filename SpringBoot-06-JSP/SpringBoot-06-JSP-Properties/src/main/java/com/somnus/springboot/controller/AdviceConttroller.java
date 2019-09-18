package com.somnus.springboot.controller;

import com.google.common.primitives.Chars;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Somnus
 * @brief
 * @details
 * @date 2018/12/6 9:53
 */
@Controller
public class AdviceConttroller {

    @GetMapping("calculate")
    @ResponseBody
    public Integer calculate(Integer num){
        System.out.println(1/num);
        return num;
    }

    @GetMapping("array")
    @ResponseBody
    public Integer array(Integer num){
        System.out.println(Chars.asList('a', 'b').get(num));
        return num;
    }

}
