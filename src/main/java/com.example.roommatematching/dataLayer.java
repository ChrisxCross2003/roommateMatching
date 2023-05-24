package com.example.roommatematching;

import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.List;

public class dataLayer {
    private static List<Student> students = new ArrayList<>();
    private static final String fileName = "roommateSheet.xlsx";
    private static final Workbook workbook = XLS_reader.loadWorkbook(fileName);
    public static void initializeAllStudents() {
        Sheet sheet;
        sheet = workbook.getSheetAt(0);
        assert sheet != null;
        DataFormatter formatter = new DataFormatter();
        for (Row row : sheet) {
            if (row.getRowNum() != 0){
                Student student = new Student();
                for (Cell cell: row) {
                    switch (cell.getColumnIndex()) {
                        case (6):
                            // Full Name
                            student.setName(cell.getStringCellValue());
                        case (7):
                            // computing ID
                            student.setID(cell.getStringCellValue());
                        case (8):
                            // preference for three
                            student.set_preference_of_roommates_for_three(cell.getStringCellValue());
                        case (9):
                            // preference for two
                            student.set_preference_of_roommates_for_two(cell.getStringCellValue());
                        case (10):
                            // preference for one
                            student.set_preference_of_roommates_for_one(cell.getStringCellValue());
                        case (11):
                            // number of roommates already
                            try {
                                student.set_number_of_roommates(cell.getNumericCellValue());
                            } catch (IllegalStateException e) {
                                student.set_number_of_roommates(0);
                            }
                        case (12):
                            // self-cleanliness
                            student.set_self_cleanliness(formatter.formatCellValue(cell));
                        case (13):
                            // other-cleanliness
                            student.set_other_cleanliness(formatter.formatCellValue(cell));
                        case (14):
                            // cleanliness-weight
                            student.set_cleanliness_weight(formatter.formatCellValue(cell));
                        case (15):
                            // self-guests
                            student.set_self_guests(formatter.formatCellValue(cell));
                        case (16):
                            // other-guests
                            student.set_other_guests(formatter.formatCellValue(cell));
                        case (17):
                            // guests weight
                            student.set_guests_weight(formatter.formatCellValue(cell));
                        case (18):
                            // hangout
                            student.set_hangout(formatter.formatCellValue(cell));
                        case (19):
                            // hangout weight
                            student.set_hangout_weight(formatter.formatCellValue(cell));
                        case (20):
                            // sleep
                            student.set_sleep(formatter.formatCellValue(cell));
                        case (21):
                            // sleep weight
                            student.set_sleep_weight(formatter.formatCellValue(cell));
                        case (22):
                            // self-extroversion
                            student.set_self_extroversion(formatter.formatCellValue(cell));
                        case (23):
                            // other-extroversion
                            student.set_other_extroversion(formatter.formatCellValue(cell));
                        case (24):
                            // extroversion weight
                            student.set_extroversion_weight(formatter.formatCellValue(cell));
                        case (25):
                            // self-presence
                            student.set_self_presence(formatter.formatCellValue(cell));
                        case (26):
                            // other-presence
                            student.set_other_presence(formatter.formatCellValue(cell));
                        case (27):
                            // presence weight
                            student.set_presence_weight(formatter.formatCellValue(cell));
                        case (28):
                            // religion
                            student.set_religion(formatter.formatCellValue(cell));
                        case (29):
                            // special information
                            student.set_special_information(formatter.formatCellValue(cell));
                        default:
                            break;
                    }
            }
                students.add(student);
            }
        }
        for (Student student : students) {
            System.out.println("Student ID: "+student.getid());
        }
    }
//    public static void matchStudents() {
//        Sheet sheet;
//        sheet = workbook.getSheetAt(0);
//        assert sheet != null;
//        for (int i = 0; i<sheet.getPhysicalNumberOfRows();i++) {
//            Row row = sheet.getRow(i);
//            // For each student, initialize their ID and preference list.
//            Student student = new Student(row.getCell(7).getStringCellValue());
//            // add student to students (list) to keep track of ALL students.
//            students.add(student);
//            int j = 0;
//            while (j < sheet.getPhysicalNumberOfRows()) {
//                if (j != i) {
//                    Row row1 = sheet.getRow(j);
//                    Student new_student = new Student(row1.getCell(7).getStringCellValue());
//                    StudentPreference preference = new StudentPreference();
//                }
//
//            }
//
//        }
//    }
}
