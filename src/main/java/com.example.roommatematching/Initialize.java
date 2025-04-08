package com.example.roommatematching;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for initializing and loading student data from the Excel spreadsheet (roommateSheet.xlsx),
 * categorizing students based on preferences, and generating preference lists for matching.
 */

public class Initialize {
    private static final List<Student> students = new ArrayList<>();
    private static Workbook workbook;

    // Primary preferences
    private static List<Student> malePairSeekers = new ArrayList<>();
    private static List<Student> femalePairSeekers = new ArrayList<>();
    private static List<Student> maleGroupSeekers = new ArrayList<>();
    private static List<Student> femaleGroupSeekers = new ArrayList<>();

    // Backup preferences
    private static List<Student> malePairBackups = new ArrayList<>();
    private static List<Student> femalePairBackups = new ArrayList<>();
    private static List<Student> maleGroupBackups = new ArrayList<>();
    private static List<Student> femaleGroupBackups = new ArrayList<>();

    /**
     * Reads all student data from the Excel sheet and populates the `students` list.
     */
    public static void initializeAllStudents(String filename) {
        InputStream stream = Initialize.class.getResourceAsStream("/"+filename);
        if (stream == null) {
            // If file can't be found, throw exception.
            throw new RuntimeException("Error: Could not load '"+filename+"'. Make sure the file exists in 'src/main/resources/' and is spelled correctly.");
        }
        try {
            workbook = new XSSFWorkbook(stream);
        } catch (IOException e) {
            // If file is in incorrect format or corrupted, throw exception.
            throw new RuntimeException("Error reading the Excel file: " + e.getMessage(), e);
        }

        Sheet sheet;
        sheet = workbook.getSheetAt(0);
        assert sheet != null;
        DataFormatter formatter = new DataFormatter();

        // Go through each row of the sheet to extract data to our program.
        for (Row row : sheet) {
            if (row.getCell(0) == null) {
                // if row is empty, no more students to add.
                break;
            } else {
                if (row.getRowNum() != 0) {
                    // initialize a new Student per Row.
                    Student student = new Student();
                    for (Cell cell : row) {
                        switch (cell.getColumnIndex()) {
                            case (5) -> {
                                // Full Name
                                // System.out.println("Name: " + cell.getStringCellValue());
                                student.setName(cell.getStringCellValue());
                            }
                            case (6) -> {
                                // computing ID
                                // System.out.println("ID: " + cell.getStringCellValue());
                                student.setID(cell.getStringCellValue());
                            }
                            case (7) -> {
                                // Gender
                                student.setGender(cell.getStringCellValue());
                                // System.out.println("Preferred Gender to House with: " + cell.getStringCellValue());
                            }
                            case (8) -> {
                                // preference for two
                                student.set_preference_of_group_of_4(cell.getStringCellValue());
                                // System.out.println("Preference for Group of 4: " + cell.getStringCellValue());
                            }
                            case (9) -> {
                                // preference for one
                                // System.out.println("Preference for Group of 2: " + cell.getStringCellValue());
                                student.set_preference_of_group_of_2(cell.getStringCellValue());
                            } case (10) -> {
                                // add swap value check. May not use.
                                String str = swapValueCheck(cell);
                                // System.out.println("Lowest Match % before Swap: " + str);
                                student.setLowest_possible_score(str);
                            }
                            case (11) -> {
                                // number of roommates already
                                double x = 0;
                                try {
                                    x = Double.parseDouble(cell.getStringCellValue());
                                    student.set_number_of_roommates(x);
                                } catch (NumberFormatException e) {
                                    student.set_number_of_roommates(0);
                                } catch (IllegalStateException e) {
                                    x = cell.getNumericCellValue();
                                    student.set_number_of_roommates(x);
                                }
                                // System.out.println("Number of roommates: " + x);
                            }
                            case (12) -> {
                                // self-cleanliness
                                // System.out.println("self-Cleanliness: " + cell.getNumericCellValue());
                                student.set_self_cleanliness(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (13) -> {
                                // other-cleanliness
                                // System.out.println("Other-Cleanliness: " + cell.getNumericCellValue());
                                student.set_other_cleanliness(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (14) -> {
                                // cleanliness-weight
                                // System.out.println("Cleanliness-Weight: " + cell.getNumericCellValue());
                                student.set_cleanliness_weight(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (15) -> {
                                // self-guests
                                // System.out.println("Self-Guests: " + cell.getNumericCellValue());
                                student.set_self_guests(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (16) -> {
                                // other-guests
                                // System.out.println("Other-Guests: " + cell.getNumericCellValue());
                                student.set_other_guests(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (17) -> {
                                // guests weight
                                // System.out.println("Guests Weight: " + cell.getNumericCellValue());
                                student.set_guests_weight(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (18) -> {
                                // hangout
                                // System.out.println("Hangout: " + cell.getNumericCellValue());
                                student.set_hangout(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (19) -> {
                                // hangout weight
                                // System.out.println("Hangout Weight: " + cell.getNumericCellValue());
                                student.set_hangout_weight(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (20) -> {
                                // sleep
                                // System.out.println("Sleep: " + cell.getStringCellValue());
                                student.set_sleep(formatter.formatCellValue(cell));
                            }
                            case (21) -> {
                                // sleep weight
                                // System.out.println("Sleep Weight: " + cell.getNumericCellValue());
                                student.set_sleep_weight(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (22) -> {
                                // self-extroversion
                                // System.out.println("Self-Extroversion: " + cell.getNumericCellValue());
                                student.set_self_extroversion(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (23) -> {
                                // other-extroversion
                                // System.out.println("Other Extroversion: " + cell.getNumericCellValue());
                                student.set_other_extroversion(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (24) -> {
                                // extroversion weight
                                // System.out.println("Extroversion Weight: " + cell.getNumericCellValue());
                                student.set_extroversion_weight(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (25) -> {
                                // self-presence
                                // System.out.println("Self-Presence: " + cell.getNumericCellValue());
                                student.set_self_presence(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (26) -> {
                                // other-presence
                                // System.out.println("Other-Presence: " + cell.getNumericCellValue());
                                student.set_other_presence(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (27) -> {
                                // presence weight
                                // System.out.println("Presence-Weight: " + cell.getNumericCellValue());
                                student.set_presence_weight(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (28) -> {
                                // religion
                                // System.out.println("Religion: " + cell.getStringCellValue());
                                student.set_religion(formatter.formatCellValue(cell));
                            }
                            case (29) -> {
                                // special information
                                // System.out.println("Special Information: " + cell.getStringCellValue());
                                student.set_special_information(formatter.formatCellValue(cell));
                            }
                            default -> {
                            }
                        }
                    }
                    System.out.println("\n");
                    // After all info is added about a student, add them to list of Students.
                    students.add(student);
                }
            }
        }
    }

    private static String swapValueCheck(Cell cell) {
        String str = cell.getStringCellValue();
        if (str.contains("25")) {
            str = "25";
        } else if (str.contains("Yes")) {
            str = "50";
        } else if (str.equals("No. Keep my match")) {
            str = "0";
        } else {
            str = "100";
        }
        return str;
    }


    /**
     * Separates students into preference-based lists for pair or group matching.
     * Then builds a preference list for each student based on compatibility with others in the same category.
     */
    public static void createSeekers() {
        final String CYAN = "\u001B[36m";
        final String RESET = "\u001B[0m";
        final String GREEN = "\u001B[32m";

        // Step 1: Categorize students based on group size preferences
        for (Student student : students) {
            String gender = student.getGender().trim().toLowerCase(); // Normalize casing
            String[] groupPrefs = student.getPreferred_group_size();
            boolean prefersGroupOfFour = "First Choice".equalsIgnoreCase(groupPrefs[0]); // index 0 = group
            boolean prefersPair = "First Choice".equalsIgnoreCase(groupPrefs[1]);        // index 1 = pair
            boolean backupGroup = "Second Choice".equalsIgnoreCase(groupPrefs[0]);
            boolean backupPair = "Second Choice".equalsIgnoreCase(groupPrefs[1]);

            boolean wantsMale = gender.equals("male");
            boolean wantsFemale = gender.equals("female");

            if (wantsMale) {
                if (prefersPair) malePairSeekers.add(student);
                else if (backupPair) malePairBackups.add(student);

                if (prefersGroupOfFour) maleGroupSeekers.add(student);
                else if (backupGroup) maleGroupBackups.add(student);
            } else if (wantsFemale) {
                if (prefersPair) femalePairSeekers.add(student);
                else if (backupPair) femalePairBackups.add(student);

                if (prefersGroupOfFour) femaleGroupSeekers.add(student);
                else if (backupGroup) femaleGroupBackups.add(student);
            }
        }
    }


    public static StableMatching initialize_matching() {
        return new StableMatching(
                malePairSeekers, femalePairSeekers,
                maleGroupSeekers, femaleGroupSeekers,
                malePairBackups, femalePairBackups,
                maleGroupBackups, femaleGroupBackups
        );
    }
}
