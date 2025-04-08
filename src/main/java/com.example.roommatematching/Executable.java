package com.example.roommatematching;

public class Executable {
    public static void main(String[] args) {
        Initialize.initializeAllStudents("roommateSheet.xlsx");
        Initialize.createSeekers();
        StableMatching matcher = Initialize.initialize_matching();
        System.out.println("\nRunning Algorithm...");
        matcher.matchStudents();

        // TODO: add test resources and full coverage the code for official testing.
        // TODO: add a final, large data file for matching. See how long it takes. (up to 5 minutes for 400 points is good).
    }
}
