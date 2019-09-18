package com.somnus.springboot.mapper.secondary;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LogMapper {

    @Insert("insert into t_log (ext) values (#{ext})")
    int saveLog(@Param("ext") String ext);
    
    @Delete("delete from t_log")
    int deleteAll();
}
