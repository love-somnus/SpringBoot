package com.somnus.springboot.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafController {

    @GetMapping(value="thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name", "SpringBoot");
        model.addAttribute("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return "thymeleaf";
    }
    
}
