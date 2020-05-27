package com.somnus.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.SneakyThrows;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author Kevin
 * @packageName com.somnus.excel
 * @title: Test
 * @description: TODO
 * @date 2020/4/27 14:28
 */
public class ExcelTest {

    @Test
    public void read(){
        EasyExcel.read("D:\\excel\\0427a.xlsx", User.class, new AnalysisEventListener<User>() {

            List<User> list = new ArrayList<>();

            //读取表头内容
            @Override
            public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
                System.out.println("表头：" + headMap);
            }

            @Override
            public void invoke(User user, AnalysisContext analysisContext) {
                System.out.println("解析到一条数据：" + user);
                list.add(user);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("excel文件解析完毕！" + list);
            }
        }).sheet().doRead();
    }

    @Test
    public void read2(){
        EasyExcel.read("D:\\excel\\0427a.xlsx", new AnalysisEventListener<Map<Integer,String>>() {

            //读取表头内容
            @Override
            public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
                System.out.println("表头：" + headMap);
            }

            @Override
            public void invoke(Map<Integer,String> rowMap, AnalysisContext analysisContext) {
                System.out.println("解析到一条数据：" + rowMap);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("excel文件解析完毕！");
            }
        }).sheet().doRead();
    }

    @Test
    public void write(){

        List<User> list = Lists.newArrayList(new User("18612222", "2222"),new User("186334222", "33333"));

        EasyExcel.write("D:\\excel\\0427w.xlsx", User.class).sheet("模板").doWrite(list);
    }

    @Test
    @SneakyThrows
    public void write2(){
        /*response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");*/

        List<User> list = Lists.newArrayList(new User("18612222", "2222"),new User("186334222", "33333"));

        OutputStream os = new FileOutputStream(new File("D:\\excel\\0427w2222.xlsx"));

        EasyExcel.write(os, User.class).sheet("模板").doWrite(list);
    }

    @Test
    public void write3(){

        List<String> head0 = new ArrayList<>();
        head0.add("字符串");
        List<String> head1 = new ArrayList<>();
        head1.add("数字");
        List<String> head2 = new ArrayList<>();
        head2.add("日期");
        List<List<String>> heads = Lists.newArrayList(head0, head1, head2);

        List<List<Object>> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<Object> data = new ArrayList<>();
            data.add("字符串" + i);
            data.add(0.58);
            data.add(new Date());
            datas.add(data);
        }

        EasyExcel.write("D:\\excel\\0427w.xlsx").head(heads).sheet("模板").doWrite(datas);
    }

}
