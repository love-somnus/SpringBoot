package com.somnus.springboot.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.somnus.springboot.mybatis.model.TCity;
import tk.mybatis.mapper.ParentMapper;

public interface TCityMapper extends ParentMapper<TCity> {

    TCity selectByCondition(Map<String,Object> map);

    TCity selectByCondition2(Map<String,Object> map);

    @MapKey("id")
    Map<String,Object> returnMap(@Param(value = "cityName") String cityName);

    @MapKey("id")
    Map<String,Object> returnMap2(@Param(value = "cityName") String cityName);

    List<Map<String,Object>> returnMap3(@Param(value = "cityName") String cityName);
}