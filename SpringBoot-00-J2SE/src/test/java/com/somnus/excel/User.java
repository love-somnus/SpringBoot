package com.somnus.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kevin
 * @packageName com.somnus.excel
 * @title: userRowModel
 * @description: TODO
 * @date 2020/4/27 14:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{

    private String mobile;

    private String code;

}