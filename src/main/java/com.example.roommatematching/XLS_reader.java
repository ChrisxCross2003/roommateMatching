package com.example.roommatematching;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.InputStream;

public class XLS_reader {
    // handles the workbook class. Keeps it private and only switches between sheets when needed.
    private static Workbook workbook;
    static Workbook loadWorkbook(String fileName) {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream(fileName);
            if (fileName.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(input);
                workbook.createSheet("groups");
            } else if (fileName.endsWith(".xls")) {
                workbook = new HSSFWorkbook(input);
                workbook.createSheet("groups");
            } else {
                throw new RuntimeException("Invalid input file type");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }
}
