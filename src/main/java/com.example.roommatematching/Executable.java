package com.example.roommatematching;

public class Executable {
    public static void main(String[] args) {
        Initialize.initializeAllStudents("largeMatching.xlsx");
        Initialize.createSeekers();
        StableMatching matcher = Initialize.initialize_matching();
        matcher.matchStudents();
    }
}
