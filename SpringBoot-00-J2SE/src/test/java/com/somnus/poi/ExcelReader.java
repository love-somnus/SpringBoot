package com.somnus.poi;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @author Somnus
 * @version 1.0
 * @Description: 读取Excel 兼容2003/2007/2010
 * @date 2015年11月6日 上午10:05:52
 */
public class ExcelReader {
    /**
     * 读取“.xls”格式使用  import org.apache.poi.hssf.usermodel.*;包的内容，例如：HSSFWorkbook
     * 读取“.xlsx”格式使用 import org.apache.poi.xssf.usermodel.*; 包的内容，例如：XSSFWorkbook
     * 读取两种格式使用    import org.apache.poi.ss.usermodel.*    包的内容，例如：Workbook
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static List<String[]> readExcel(String filePath) throws Exception {
        String fullPath = Thread.currentThread().getContextClassLoader().getResource(filePath).getPath();

        InputStream in = new BufferedInputStream(new FileInputStream(fullPath));

        Workbook wb = WorkbookFactory.create(in);

        List<String[]> list = new ArrayList<String[]>();
        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
            Sheet st = wb.getSheetAt(sheetIndex);
            // 第一行为标题，不取
            for (int rowIndex = 0; rowIndex <= st.getPhysicalNumberOfRows(); rowIndex++) {
                Row row = st.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                String[] cells = cellArray(row);
                list.add(cells);
            }
        }
        return list;
    }

    @SuppressWarnings("deprecation")
    private static String[] cellArray(Row row) {
        String[] cellArray = new String[row.getPhysicalNumberOfCells()];
        for (int index = 0; index < row.getPhysicalNumberOfCells(); index++) {
            String value = "";
            Cell cell = row.getCell(index);
            if (cell != null) {
                switch (cell.getCellTypeEnum()) {
                    case STRING:
                        value = cell.getStringCellValue();
                        break;
                    case NUMERIC:
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                            Date date = cell.getDateCellValue();
                            if (date != null) {
                                value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                            } else {
                                value = "";
                            }
                        } else {
                            value = new DecimalFormat("0.00").
                                    format(cell.getNumericCellValue());
                        }
                        break;
                    case FORMULA:
                        try {
                            value = String.valueOf(cell.getNumericCellValue());
                        } catch (IllegalStateException e) {
                            value = String.valueOf(cell.getRichStringCellValue());
                        }
                        break;
                    case BLANK:
                        break;
                    case ERROR:
                        value = "";
                        break;
                    case BOOLEAN:
                        value = (cell.getBooleanCellValue() == true ? "Y" : "N");
                        break;
                    default:
                        value = "";
                }
            } else {
                value = "";
            }
            cellArray[index] = value;
        }
        return cellArray;
    }

}
