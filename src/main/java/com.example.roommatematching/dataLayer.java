package com.example.roommatematching;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class dataLayer {
    private static final String fileName = "roommateSheet.xlsx";
    private static XLS_reader reader = new XLS_reader();
    public static void createHash_xlsx() throws IOException {
        Sheet sheet;
        sheet = XLS_reader.getSheet(fileName);
//        sheet.removeRow(sheet.getRow(0));
        for (Row row : sheet) {
            System.out.println(row.getSheet());
        }
    }
}
