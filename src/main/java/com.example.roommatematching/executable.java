package com.example.roommatematching;

public class executable {
    public static void main(String[] args) {
        System.out.println("Ingesting Students...");
        Initialize.initializeAllStudents();
        System.out.println("Creating Preferences...");
        Initialize.createPreferenceLists();
        stableMatching matcher = Initialize.initialize_matching();
        System.out.println("Running Algorithm...");
        matcher.matchStudents();
    }
}
