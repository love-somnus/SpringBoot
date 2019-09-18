package com.somnus.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.somnus.springboot.mybatis.mapper.TCityMapper;
import com.somnus.springboot.mybatis.model.TCity;

@Controller
public class MybatisController {

    @Autowired
    private TCityMapper mapper;

    @GetMapping("/mybatis")
    public String mybatis(@RequestParam(name="cityName",defaultValue="鹰潭") String cityName, Model model) {
        TCity condition = new TCity();
        condition.setCityName("鹰潭");
        TCity city = mapper.selectOne(condition);
        model.addAttribute("city", city);
        return "mybatis";
    }

}
