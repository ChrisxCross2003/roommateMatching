package com.example.roommatematching;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;

public class XLS_reader {
    static Sheet getSheet(String fileName) throws IOException {
        FileInputStream input = new FileInputStream(fileName);
        Workbook workbook;
        if (fileName.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(input);
            return workbook.getSheetAt(0);
        } else if (fileName.endsWith(".xls")) {
            workbook = new HSSFWorkbook(input);
            return workbook.getSheetAt(0);
        } else {
            throw new RuntimeException("Invalid input file type");
        }
    }
}
