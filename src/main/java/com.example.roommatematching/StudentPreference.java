package com.example.roommatematching;

public class StudentPreference {
    public int match_score;
    public String computing_id;

    public void create_preference(int score, String id) {
        this.match_score = score;
        this.computing_id = id;
    }

    public static void calculate_preference(Student student1, Student student2) {

    }
}
