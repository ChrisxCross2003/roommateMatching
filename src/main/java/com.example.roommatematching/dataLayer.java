package com.example.roommatematching;

import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.List;

public class dataLayer {
    private static final List<Student> students = new ArrayList<>();
    private static final String fileName = "roommateSheet.xlsx";
    private static final Workbook workbook = XLS_reader.loadWorkbook(fileName);
    public static void initializeAllStudents() {
        Sheet sheet;
        sheet = workbook.getSheetAt(0);
        assert sheet != null;
        DataFormatter formatter = new DataFormatter();
        for (Row row : sheet) {
            if (row.getCell(0) == null) {
                break;
            } else {
                if (row.getRowNum() != 0) {
                    Student student = new Student();
                    for (Cell cell : row) {
                        switch (cell.getColumnIndex()) {
                            case (6) -> {
                                // Full Name
                                System.out.println("Name: " + cell.getStringCellValue());
                                student.setName(cell.getStringCellValue());
                            }
                            case (7) -> {
                                // computing ID
                                System.out.println("ID: " + cell.getStringCellValue());
                                student.setID(cell.getStringCellValue());
                            }
                            case (8) -> {
                                // preference for three
                                student.set_preference_of_roommates_for_three(cell.getStringCellValue());
                                System.out.println("Preference for three: " + cell.getStringCellValue());
                            }
                            case (9) -> {
                                // preference for two
                                student.set_preference_of_roommates_for_two(cell.getStringCellValue());
                                System.out.println("Preference for two: " + cell.getStringCellValue());
                            }
                            case (10) -> {
                                // preference for one
                                System.out.println("Preference for one: " + cell.getStringCellValue());
                                student.set_preference_of_roommates_for_one(cell.getStringCellValue());
                            }
                            case (11) -> {
                                // number of roommates already
                                try {
                                    student.set_number_of_roommates(cell.getNumericCellValue());
                                } catch (IllegalStateException e) {
                                    student.set_number_of_roommates(0);
                                }
                                System.out.println("Number of roommates: " + cell.getStringCellValue());
                            }
                            case (12) -> {
                                // self-cleanliness
                                System.out.println("self-Cleanliness: " + cell.getNumericCellValue());
                                student.set_self_cleanliness(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (13) -> {
                                // other-cleanliness
                                System.out.println("Other-Cleanliness: " + cell.getNumericCellValue());
                                student.set_other_cleanliness(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (14) -> {
                                // cleanliness-weight
                                System.out.println("Cleanliness-Weight: " + cell.getNumericCellValue());
                                student.set_cleanliness_weight(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (15) -> {
                                // self-guests
                                System.out.println("Self-Guests: " + cell.getNumericCellValue());
                                student.set_self_guests(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (16) -> {
                                // other-guests
                                System.out.println("Other-Guests: " + cell.getNumericCellValue());
                                student.set_other_guests(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (17) -> {
                                // guests weight
                                System.out.println("Guests Weight: " + cell.getNumericCellValue());
                                student.set_guests_weight(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (18) -> {
                                // hangout
                                System.out.println("Hangout: " + cell.getNumericCellValue());
                                student.set_hangout(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (19) -> {
                                // hangout weight
                                System.out.println("Hangout Weight: " + cell.getNumericCellValue());
                                student.set_hangout_weight(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (20) -> {
                                // sleep
                                System.out.println("Sleep: " + cell.getStringCellValue());
                                student.set_sleep(formatter.formatCellValue(cell));
                            }
                            case (21) -> {
                                // sleep weight
                                System.out.println("Sleep Weight: " + cell.getNumericCellValue());
                                student.set_sleep_weight(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (22) -> {
                                // self-extroversion
                                System.out.println("Self-Extroversion: " + cell.getNumericCellValue());
                                student.set_self_extroversion(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (23) -> {
                                // other-extroversion
                                System.out.println("Other Extroversion: " + cell.getNumericCellValue());
                                student.set_other_extroversion(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (24) -> {
                                // extroversion weight
                                System.out.println("Extroversion Weight: " + cell.getNumericCellValue());
                                student.set_extroversion_weight(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (25) -> {
                                // self-presence
                                System.out.println("Self-Presence: " + cell.getNumericCellValue());
                                student.set_self_presence(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (26) -> {
                                // other-presence
                                System.out.println("Other-Presence: " + cell.getNumericCellValue());
                                student.set_other_presence(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (27) -> {
                                // presence weight
                                System.out.println("Presence-Weight: " + cell.getNumericCellValue());
                                student.set_presence_weight(Integer.parseInt(formatter.formatCellValue(cell)));
                            }
                            case (28) -> {
                                // religion
                                System.out.println("Religion: " + cell.getStringCellValue());
                                student.set_religion(formatter.formatCellValue(cell));
                            }
                            case (29) -> {
                                // special information
                                System.out.println("Special Information: " + cell.getStringCellValue());
                                student.set_special_information(formatter.formatCellValue(cell));
                            }
                            default -> {
                            }
                        }
                    }
                    System.out.println("\n");
                    students.add(student);
                }
            }
        }
    }
    public static void createPreferenceLists() {
        for (int i=0;i<students.size();i++) {
            students.get(i).clear_preference_list();
            for (int j=0;j<students.size();j++) {
                if (j != i) {
                    // for every student other than the one we are inquiring, calculate their match scores
                    // and add their preference to their list.
                    StudentPreference preference = new StudentPreference(students.get(i), students.get(j));
                    students.get(i).add_preference(preference);
                }
            }
            students.get(i).print_preference_list();
        }
    }
}
