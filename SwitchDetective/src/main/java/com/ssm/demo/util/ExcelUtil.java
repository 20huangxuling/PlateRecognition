package com.ssm.demo.util;

import com.ssm.demo.model.Switch;
import com.ssm.demo.service.define.SwitchListService;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.io.*;
import java.util.List;

public class ExcelUtil {
    private static String bringPath = "G:\\大创\\bring.xls";
    private static Logger logger = Logger.getLogger(ExcelUtil.class);

    SwitchListService switchListService;

    public ExcelUtil(SwitchListService switchListService) {
        this.switchListService = switchListService;
    }

    public void insetData() {
        File excelFile = null;
        InputStream inputStream = null;
        String cellStr;
        excelFile = new File(bringPath);
        try {
            inputStream = new FileInputStream(excelFile);
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                Switch s = new Switch();
                HSSFRow row = sheet.getRow(i);
                if (row == null) break;
                for (int j = 0; j <= row.getLastCellNum(); j++) {
                    HSSFCell cell = row.getCell(j);
                    switch (j) {
                        case 0:
                            s.setName(cell.getStringCellValue());
                            break;
                        case 1:
                            s.setNote("");
                            break;
                        case 2:
                            s.setStandardState(cell.getStringCellValue());
                            break;
                        case 3:
                            cell.setCellType(CellType.STRING);
                            s.setCabinetId(cell.getStringCellValue());
                            break;
                        case 4:
                            s.setRow((int) cell.getNumericCellValue());
                            break;
                        case 5:
                            s.setColumn((int) cell.getNumericCellValue());
                            break;
                        case 6:
                            cell.setCellType(CellType.STRING);
                            s.setId(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                }
                switchListService.insertSwitch(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createTable(String filePath, String sheetName, String[] titles, List<String[]> rows) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);
        sheet.setDefaultColumnWidth(20);
        HSSFRow firstRow = sheet.createRow(0);
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        HSSFCell cell = null;
        for (int i = 0; i < titles.length; i++) {
            cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(style);
        }
        int j = 0;
        for (String[] values : rows) {
            firstRow = sheet.createRow(++j);
            for (int i = 0; i < titles.length; i++) {
                firstRow.createCell(i).setCellValue(values[i]);
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        wb.write(fileOutputStream);
        fileOutputStream.close();
    }
}
