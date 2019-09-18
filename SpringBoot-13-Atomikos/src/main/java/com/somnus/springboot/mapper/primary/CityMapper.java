package com.somnus.springboot.mapper.primary;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CityMapper {

    @Insert("insert into t_city (city_name, description) values (#{cityName}, #{description})")
    int saveCity(@Param("cityName") String cityName, @Param("description") String description);

    @Delete("delete from t_city")
    int deleteAll();
}
