package com.somnus.springboot.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ThymeleafController {

    @GetMapping(value="thymeleaf")
    public String thymeleaf(Model model, HttpServletResponse response){
        model.addAttribute("name", "SpringBoot");
        model.addAttribute("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Cookie cookie = new Cookie("session-id", "abc");

        response.addCookie(cookie);
        return "thymeleaf";
    }

    @GetMapping(value="hello")
    public String thymeleaf(@CookieValue(value = "session-id", defaultValue = "1234") String sessionid){

        return "thymeleaf"+sessionid;
    }
    
}
