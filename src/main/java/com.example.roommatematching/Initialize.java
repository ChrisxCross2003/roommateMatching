package com.example.roommatematching;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Initialize {
    private static final List<Student> students = new ArrayList<>();
    private static final InputStream stream = Initialize.class.getResourceAsStream("/roommateSheet.xlsx");
    private static final Workbook workbook;

    static {
        if (stream == null) {
            // Custom error message
            throw new RuntimeException("Error: Could not load 'roommateSheet.xlsx'. Make sure the file exists in 'src/main/resources/' and is spelled correctly.");
        }

        try {
            workbook = new XSSFWorkbook(stream);
        } catch (IOException e) {
            throw new RuntimeException("Error reading the Excel file: " + e.getMessage(), e);
        }
    }


    public static void initializeAllStudents() {
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
                            // TODO: ADD GENDER!!!
                            case (5) -> {
                                // Full Name
                                System.out.println("Name: " + cell.getStringCellValue());
                                student.setName(cell.getStringCellValue());
                            }
                            case (6) -> {
                                // computing ID
                                System.out.println("ID: " + cell.getStringCellValue());
                                student.setID(cell.getStringCellValue());
                            }
                            case (7) -> {
                                // preference for two
                                student.set_preference_of_group_of_4(cell.getStringCellValue());
                                System.out.println("Preference for Group of 4: " + cell.getStringCellValue());
                            }
                            case (8) -> {
                                // preference for one
                                System.out.println("Preference for Group of 2: " + cell.getStringCellValue());
                                student.set_preference_of_group_of_2(cell.getStringCellValue());
                            } case (9) -> {
                                // add swap value check. May not use.
                                String str = swapValueCheck(cell);
                                System.out.println("Lowest Match % before Swap: " + str);
                                student.setLowest_possible_score(str);
                            }
                            case (10) -> {
                                // number of roommates already
                                try {
                                    student.set_number_of_roommates(cell.getNumericCellValue());
                                } catch (IllegalStateException e) {
                                    student.set_number_of_roommates(0);
                                }
                                System.out.println("Number of roommates: " + cell.getStringCellValue());
                            }
                            case (11) -> {
                                // self-cleanliness
                                System.out.println("self-Cleanliness: " + cell.getNumericCellValue());
                                student.set_self_cleanliness(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (12) -> {
                                // other-cleanliness
                                System.out.println("Other-Cleanliness: " + cell.getNumericCellValue());
                                student.set_other_cleanliness(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (13) -> {
                                // cleanliness-weight
                                System.out.println("Cleanliness-Weight: " + cell.getNumericCellValue());
                                student.set_cleanliness_weight(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (14) -> {
                                // self-guests
                                System.out.println("Self-Guests: " + cell.getNumericCellValue());
                                student.set_self_guests(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (15) -> {
                                // other-guests
                                System.out.println("Other-Guests: " + cell.getNumericCellValue());
                                student.set_other_guests(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (16) -> {
                                // guests weight
                                System.out.println("Guests Weight: " + cell.getNumericCellValue());
                                student.set_guests_weight(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (17) -> {
                                // hangout
                                System.out.println("Hangout: " + cell.getNumericCellValue());
                                student.set_hangout(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (18) -> {
                                // hangout weight
                                System.out.println("Hangout Weight: " + cell.getNumericCellValue());
                                student.set_hangout_weight(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (19) -> {
                                // sleep
                                System.out.println("Sleep: " + cell.getStringCellValue());
                                student.set_sleep(formatter.formatCellValue(cell));
                            }
                            case (20) -> {
                                // sleep weight
                                System.out.println("Sleep Weight: " + cell.getNumericCellValue());
                                student.set_sleep_weight(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (21) -> {
                                // self-extroversion
                                System.out.println("Self-Extroversion: " + cell.getNumericCellValue());
                                student.set_self_extroversion(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (22) -> {
                                // other-extroversion
                                System.out.println("Other Extroversion: " + cell.getNumericCellValue());
                                student.set_other_extroversion(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (23) -> {
                                // extroversion weight
                                System.out.println("Extroversion Weight: " + cell.getNumericCellValue());
                                student.set_extroversion_weight(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (24) -> {
                                // self-presence
                                System.out.println("Self-Presence: " + cell.getNumericCellValue());
                                student.set_self_presence(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (25) -> {
                                // other-presence
                                System.out.println("Other-Presence: " + cell.getNumericCellValue());
                                student.set_other_presence(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (26) -> {
                                // presence weight
                                System.out.println("Presence-Weight: " + cell.getNumericCellValue());
                                student.set_presence_weight(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (27) -> {
                                // religion
                                System.out.println("Religion: " + cell.getStringCellValue());
                                student.set_religion(formatter.formatCellValue(cell));
                            }
                            case (28) -> {
                                // special information
                                System.out.println("Special Information: " + cell.getStringCellValue());
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

    public static void createPreferenceLists() {
        final String CYAN = "\u001B[36m";
        final String RESET = "\u001B[0m";
        final String GREEN = "\u001B[32m";

        // TODO: Add pair and group backups.
        List<Student> pairSeekers = new ArrayList<>();
        List<Student> groupOfFourSeekers = new ArrayList<>();
        // TODO: Separate by Male and Female. (If other, put who they'd like to match with more).

        // Step 1: Categorize students based on group size preferences
        for (Student student : students) {
            String[] groupPrefs = student.getPreferred_group_size();
            boolean prefersPairs = "Yes".equalsIgnoreCase(groupPrefs[1]);
            boolean prefersGroups = "Yes".equalsIgnoreCase(groupPrefs[0]);

            // You can tweak this logic based on how strictly you want to filter
            if (prefersPairs && !prefersGroups) {
                pairSeekers.add(student);
            } else if (prefersGroups && !prefersPairs) {
                groupOfFourSeekers.add(student);
            } else {
                // If they said yes to both (or didn't specify), you can either:
                // - put them in both groups, or
                // - put them in a hybrid bucket (optional).
                pairSeekers.add(student);
                groupOfFourSeekers.add(student);
            }
        }

        // Step 2: Generate preferences within each group
        System.out.println(GREEN +  "\n" + "Preferences for PairSeekers" + RESET);
        generatePreferencesWithinGroup(pairSeekers);
        System.out.println(CYAN + "\n" + "Preferences for GroupSeekers" + RESET);
        generatePreferencesWithinGroup(groupOfFourSeekers);

        System.out.println(GREEN + "\n" + "Initialization Successful. Running Algorithm..." + RESET);
        // runAlgorithm();
    }

    // Helper method to reduce duplicate logic
    private static void generatePreferencesWithinGroup(List<Student> group) {
        for (int i = 0; i < group.size(); i++) {
            Student student = group.get(i);
            student.clear_preference_list();

            for (int j = 0; j < group.size(); j++) {
                if (i != j) {
                    StudentPreference preference = new StudentPreference(student, group.get(j));
                    student.add_preference(preference);
                }
            }
            student.print_preference_list();
        }
    }
    public static void runAlgorithm() {
        // TODO: Create Matching Algorithm
        // Have the algorithm prioritize large groups first.
        // Doesn't matter which gender starts first. Just need to keep track of each group.
        // Run Happy Marriage match on Groups of 4.
            // if 1 person remains, add best match to Pairbackup.
            // if 2 or 3 people remain, add 1-2 people from groupbackup.
            // I am doing this so large group matches help more people.
        // After Groups of 4 are matched, match pairs.
            // if 1 person still remains. They're the odd one out.

        stableMatching.stableRoommateAlgorithm(students);
    }
}
