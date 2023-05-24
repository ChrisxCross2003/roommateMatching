package com.example.roommatematching;

import java.util.List;

public class Student {
    private final String[] preferred_amount_of_roommates = new String[3];
    private double number_of_roommates;
    private String name;
    private String id;
    private static String groupID;
    private static List<StudentPreference> preference_list;

    private String self_cleanliness;
    private String other_cleanliness;
    private String cleanliness_weight;

    private String self_guests;
    private String other_guests;
    private String guests_weight;

    private String hangout;
    private String hangout_weight;

    private String sleep;
    private String sleep_weight;

    private String self_extroversion;
    private String other_extroversion;
    private String extroversion_weight;

    private String self_presence;
    private String other_presence;
    private String presence_weight;

    private String religion;
    private String special_information;

    public Student() {
    }

    public static void setGroup(String id) {
        groupID = id;
    }
    public void setID(String computing_id) {
         this.id = computing_id;
    }
    public void setName(String n) {
        this.name = n;
    }
    public void set_preference_of_roommates_for_three(String x) {
        this.preferred_amount_of_roommates[0] = x;
    }
    public void set_preference_of_roommates_for_two(String x) {
        this.preferred_amount_of_roommates[1] = x;
    }
    public void set_preference_of_roommates_for_one(String x) {
        this.preferred_amount_of_roommates[2] = x;
    }
    public void set_number_of_roommates(double x) {
        this.number_of_roommates = x;
    }
    public void set_self_cleanliness(String x) {
        this.self_cleanliness = x;
    }
    public void set_other_cleanliness(String x) {
        this.other_cleanliness = x;
    }
    public void set_cleanliness_weight(String x) {
        this.cleanliness_weight = x;
    }
    public void set_self_guests(String x) {
        this.self_guests = x;
    }
    public void set_other_guests(String x) {
        this.other_guests = x;
    }
    public void set_guests_weight(String x) {
        this.guests_weight = x;
    }

    public void set_hangout(String x) {
        this.hangout = x;
    }
    public void set_hangout_weight(String x) {
        this.hangout_weight = x;
    }
    public void set_sleep(String x) {
        this.sleep = x;
    }
    public void set_sleep_weight(String x) {
        this.sleep_weight = x;
    }
    public void set_self_extroversion(String x) {
        this.self_extroversion = x;
    }
    public void set_other_extroversion(String x) {
        this.other_extroversion = x;
    }
    public void set_extroversion_weight(String x) {
        this.extroversion_weight = x;
    }
    public void set_self_presence(String x) {
        this.self_presence = x;
    }
    public void set_other_presence(String x) {
        this.other_presence = x;
    }
    public void set_presence_weight(String x) {
        this.presence_weight = x;
    }
    public void set_religion(String x) {
        this.religion = x;
    }
    public void set_special_information(String x) {
        this.special_information = x;
    }
    public static String getGroup() {
        return groupID;
    }
    public static void add_preference(StudentPreference preference) {
        preference_list.add(preference);
    }

    public static void remove_preference(StudentPreference preference) {
        preference_list.remove(preference);
    }

    // Should only be used for debugging as this information should be hidden.
    public static void print_preference_list() {
        for (StudentPreference preference : preference_list) {
            System.out.println("ID: " + preference.computing_id);
            System.out.println("Match Score: " + preference.match_score);
        }
    }


    public String getid() {
        return this.id;
    }

}
