package com.somnus.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.somnus.springboot.mybatis.mapper.CityMapper;
import com.somnus.springboot.mybatis.model.City;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MybatisController {

    @Autowired
    private CityMapper mapper;

    @GetMapping("/mybatis")
    public String mybatis(@RequestParam(name="cityName",defaultValue="鹰潭") String cityName, Model model) {
        City city = mapper.findByCityName(cityName);
        model.addAttribute("city", city);
        log.trace("trace★★★★★★★★★★");
        log.debug("debug☆☆☆☆☆☆☆☆☆☆");
        log.info("info卍卍卍卍卍卍卍卍卍卍卍卍卍卍卍卍");
        log.warn("warn♫♫♫♫♫♫♫♫♫♫♫♫♫♫♫♫♫♫♫♫♫♫");
        log.error("error※※※※※※※※※※");
        return "mybatis";
    }

}
