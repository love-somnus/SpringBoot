package com.somnus.poi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.somnus.apache.People;

/**
 * @author Somnus
 * @version V1.0
 * @Description: TODO
 * @date 2015年11月6日 上午11:22:57
 */
public class ExcelTestCase {

    @Test
    public void read() throws Exception {
        List<String[]> list = ExcelReader.readExcel("excel/80034.xls");
        for (String[] arr : list) {
            System.out.println(Arrays.toString(arr));
        }
    }

    /**
     * 50W 数据量
     * SXSSFWorkbook
     *
     * @throws Exception
     */
    @Test
    public void createMax() throws Exception {
        List<People> list = new ArrayList<People>();
        for (int i = 0; i < 5000; i++) {
            People people = new People("admin" + i, "password" + i, null, BigDecimal.ONE);
            list.add(people);
        }
        MaxExcelWriter writer = new MaxExcelWriter();
        byte[] buff = writer.exportExcel("测试例子", new String[]{"用户名", "密码"}, list, null);
        OutputStream os = new FileOutputStream(new File("E:/mxSomnus.xlsx"));
        IOUtils.write(buff, os);
    }
}