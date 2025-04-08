package com.example.roommatematching;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Export {

    public static void exportGroupsToExcel(List<Group> groups, List<Student> oddMenOut, String filePath) {
        Workbook workbook = new XSSFWorkbook();

        try {
            // Sheet 1: Group Results
            Sheet groupSheet = workbook.createSheet("Group Results");

            // Header row
            String[] headers = {
                    "Group ID",
                    "Student 1 ID",
                    "Student 1 Name",
                    "Student 2 ID",
                    "Student 2 Name",
                    "Student 3 ID",
                    "Student 3 Name",
                    "Student 4 ID",
                    "Student 4 Name",
                    "Overall Compatibility",
                    "S1 → S2 Compatibility",
                    "S2 → S3 Compatibility",
                    "S3 → S4 Compatibility"
            };

            Row headerRow = groupSheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

// Data rows for groups
            int rowNum = 1;
            for (Group group : groups) {
                Row row = groupSheet.createRow(rowNum++);
                List<Student> members = group.getActualStudents();

                // Group ID
                row.createCell(0).setCellValue(group.getGroupID());

                // Student ID + Name columns
                int cellIndex = 1;
                for (int i = 0; i < 4; i++) {
                    if (i < members.size()) {
                        Student student = members.get(i);
                        String id = (student.getID() != null && !student.getID().isEmpty()) ? student.getID() : "N/A";
                        String name = (student.getName() != null && !student.getName().isEmpty()) ? student.getName() : "N/A";
                        row.createCell(cellIndex++).setCellValue(id);
                        row.createCell(cellIndex++).setCellValue(name);
                    } else {
                        // Fill remaining cells with "N/A" if group has fewer than 4 students
                        row.createCell(cellIndex++).setCellValue("N/A"); // ID
                        row.createCell(cellIndex++).setCellValue("N/A"); // Name
                    }
                }

                // Compatibility scores
                row.createCell(cellIndex++).setCellValue(roundToTwoDecimals(calculateOverallCompatibility(members)));

                for (int i = 0; i < 3; i++) {
                    if (i + 1 < members.size()) {
                        double score = calculateCompatibilityScore(members.get(i), members.get(i + 1));
                        row.createCell(cellIndex++).setCellValue(roundToTwoDecimals(score));
                    } else {
                        row.createCell(cellIndex++).setCellValue("N/A");
                    }
                }
            }

            for (int i = 0; i < headers.length; i++) {
                groupSheet.autoSizeColumn(i);
            }

            // Sheet 2: Odd Men Out
            writeOddMenOutSheet(workbook, oddMenOut);

            // Save the workbook
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
                System.out.println("✅ Excel export complete: " + filePath);
            }

        } catch (IOException e) {
            System.err.println("❌ Failed to write Excel file: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                System.err.println("❌ Failed to close workbook: " + e.getMessage());
            }
        }
    }

    private static void writeOddMenOutSheet(Workbook workbook, List<Student> oddMenOut) {
        Sheet sheet = workbook.createSheet("Odd Men Out");

        // Header Row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Student ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Gender");
        headerRow.createCell(3).setCellValue("Reason");

        // Write student rows
        int rowNum = 1;
        for (Student student : oddMenOut) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(student.getID());
            row.createCell(1).setCellValue(student.getName());
            row.createCell(2).setCellValue(student.getGender());
            row.createCell(3).setCellValue("No compatible match or backup found");
        }

        // Auto-size columns for neatness
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static double calculateOverallCompatibility(List<Student> members) {
        double totalScore = 0;
        int comparisons = 0;

        for (int i = 0; i < members.size(); i++) {
            Student a = members.get(i);
            if (isPhantomStudent(a)) continue;
            for (int j = i + 1; j < members.size(); j++) {
                Student b = members.get(j);
                if (isPhantomStudent(b)) continue;
                double score = calculateCompatibilityScore(a, b);
                totalScore += score;
                comparisons++;
            }
        }
        return (comparisons > 0) ? totalScore / comparisons : 0;
    }

    private static double calculateCompatibilityScore(Student a, Student b) {
        StudentPreference preference = new StudentPreference(a, b);

        return preference.match_score;
    }

    private static boolean isPhantomStudent(Student student) {
        return StableMatching.isPhantomStudent(student);
    }

    private static double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
