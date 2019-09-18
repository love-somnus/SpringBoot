package com.somnus.poi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

public class MaxExcelWriter {

    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     *
     * @param title   表格标题名
     * @param headers 表格属性列名数组
     * @param dataSet 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out     与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */

    public <T> byte[] exportExcel(String title, String[] headers, Collection<T> dataSet, String pattern) {
        // 声明一个工作薄
        SXSSFWorkbook workbook = new SXSSFWorkbook(10000);
        // 生成一个表格
        // 生成一个样式
        CellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.WHITE.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        // 生成一个字体
        Font font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        CellStyle contextstyle = workbook.createCellStyle();
        contextstyle.setFillForegroundColor(HSSFColor.WHITE.index);
        contextstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        contextstyle.setBorderBottom(BorderStyle.THIN);
        contextstyle.setBorderLeft(BorderStyle.THIN);
        contextstyle.setBorderRight(BorderStyle.THIN);
        contextstyle.setBorderTop(BorderStyle.THIN);
        contextstyle.setAlignment(HorizontalAlignment.CENTER);
        contextstyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 生成另一个字体
        Font font2 = workbook.createFont();
        font2.setBold(true);
        // 把字体应用到当前的样式
        contextstyle.setFont(font2);
        Sheet sheet = null;
        Row row = null;
        int index = 0;
        int sheetnum = 0;
        Iterator<T> it = dataSet.iterator();
        // 生成表格标题行
        if (title == null) {
            sheet = workbook.createSheet();
        } else {
            sheet = workbook.createSheet(title + sheetnum);
        }
        sheet.setDefaultColumnWidth(15);
        row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(style);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        ByteArrayOutputStream baos = null;
        try {
            // 生成表格具体数据行
            while (it.hasNext()) {
                index++;
                // 如果数据大于5000行，生成下一个sheet
                if (index > 50000) {
                    index = 0;
                    ++sheetnum;
                    if (title == null) {
                        sheet = workbook.createSheet();
                    } else {
                        sheet = workbook.createSheet(title + sheetnum);
                    }
                    sheet.setDefaultColumnWidth(15);
                    row = sheet.createRow(0);
                    for (int i = 0; i < headers.length; i++) {
                        Cell cell = row.createCell(i);
                        cell.setCellStyle(style);
                        XSSFRichTextString text = new XSSFRichTextString(headers[i]);
                        cell.setCellValue(text);
                    }
                }
                row = sheet.createRow(index);
                T t = (T) it.next();
                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                Field[] fields = t.getClass().getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    String fieldName = field.getName();

                    Object data = PropertyUtils.getProperty(t, fieldName);

                    Cell contentCell = row.createCell(i);

                    Boolean isNum = false;//data是否为数值型
                    Boolean isInteger = false;//data是否为整数
                    Boolean isPercent = false;//data是否为百分数
                    Boolean isNumButString = false;//data为整数但是字符型
                    if (data != null || "".equals(data)) {
                        //判断data是否为数值型
                        isNum = data.toString().matches("^(-?\\d+)(\\.\\d+)?$");
                        //判断data是否为整数（小数部分是否为0）
                        isInteger = data.toString().matches("^[-\\+]?[\\d]*$");
                        //判断data是否为百分数（是否包含“%”）
                        isPercent = data.toString().contains("%");
                        isNumButString = data instanceof String;
                    }

                    //如果单元格内容是数值类型，涉及到金钱（金额、本、利），则设置cell的类型为数值型，设置data的类型为数值类型
                    if (isNum && !isPercent) {
                        DataFormat df = workbook.createDataFormat(); // 此处设置数据格式
                        if (isInteger && !isNumButString) {
                            contextstyle.setDataFormat(df.getFormat("0"));//数据格式只显示整数
                            // 设置单元格内容为int类型
                            contentCell.setCellValue(Integer.valueOf(data.toString()));
                        } else if (isInteger && isNumButString) {
                            //do nothing //数据格式依然显示字符
                            contentCell.setCellValue(data == null ? "" : data.toString());
                        } else {
                            contextstyle.setDataFormat(df.getFormat("0.00"));//保留两位小数点
                            // 设置单元格内容为double类型
                            contentCell.setCellValue(Double.parseDouble(data.toString()));
                        }
                        // 设置单元格格式
                        contentCell.setCellStyle(contextstyle);
                    } else {
                        contentCell.setCellStyle(contextstyle);
                        // 设置单元格内容为字符型
                        contentCell.setCellValue(data == null ? "" : data.toString());
                    }

                }
            }
            baos = new ByteArrayOutputStream();
            workbook.write(baos);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("poi处理出错");
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos.toByteArray();
    }
}
