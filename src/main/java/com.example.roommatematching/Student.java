package com.example.roommatematching;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String[] preferred_group_size = new String[2];
    private double number_of_roommates;
    private String name;
    private String id;
    private String gender;
    private static int groupID = -1;
    private static final List<StudentPreference> preference_list = new ArrayList<>();
    private String lowest_possible_score;

    private double self_cleanliness;
    private double other_cleanliness;
    private double cleanliness_weight;

    private double self_guests;
    private double other_guests;
    private double guests_weight;

    private double hangout;
    private double hangout_weight;

    private String sleep;
    private double sleep_weight;

    private double self_extroversion;
    private double other_extroversion;
    private double extroversion_weight;

    private double self_presence;
    private double other_presence;
    private double presence_weight;

    private String religion;
    private String special_information;

    public Student() {
    }


    public void setID(String computing_id) {
        this.id = computing_id;
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setGender(String n) {
        this.gender = n;
    }

    public void set_preference_of_group_of_4(String x) {
        this.preferred_group_size[0] = x;
    }

    public void set_preference_of_group_of_2(String x) {
        this.preferred_group_size[1] = x;
    }

    public void set_number_of_roommates(double x) {
        this.number_of_roommates = x;
    }

    public void set_self_cleanliness(double x) {
        this.self_cleanliness = x;
    }

    public void set_other_cleanliness(double x) {
        this.other_cleanliness = x;
    }

    public void set_cleanliness_weight(double x) {
        this.cleanliness_weight = x;
    }

    public void set_self_guests(double x) {
        this.self_guests = x;
    }

    public void set_other_guests(double x) {
        this.other_guests = x;
    }

    public void set_guests_weight(double x) {
        this.guests_weight = x;
    }

    public void set_hangout(double x) {
        this.hangout = x;
    }

    public void set_hangout_weight(double x) {
        this.hangout_weight = x;
    }

    public void set_sleep(String x) {
        this.sleep = x;
    }

    public void set_sleep_weight(double x) {
        this.sleep_weight = x;
    }

    public void set_self_extroversion(double x) {
        this.self_extroversion = x;
    }

    public void set_other_extroversion(double x) {
        this.other_extroversion = x;
    }

    public void set_extroversion_weight(double x) {
        this.extroversion_weight = x;
    }

    public void set_self_presence(double x) {
        this.self_presence = x;
    }

    public void set_other_presence(double x) {
        this.other_presence = x;
    }

    public void set_presence_weight(double x) {
        this.presence_weight = x;
    }

    public void set_religion(String x) {
        this.religion = x;
    }

    public void set_special_information(String x) {
        this.special_information = x;
    }

    public static int getGroup() {
        return groupID;
    }

    public void setLowest_possible_score(String x) {
        this.lowest_possible_score = x;
    }


    // Should only be used for debugging as this information should be hidden.
    public void print_preference_list() {
        System.out.print("Student Inquired: " + this.id + "\n[");
        for (int i = 0; i < preference_list.size(); i++) {
            StudentPreference preference = preference_list.get(i);

            // Print the ID and match score as a string
            System.out.print(preference.computing_id + ", " + preference.match_score);

            // Add a separator if it's not the last element in the list
            if (i < preference_list.size() - 1) {
                System.out.print("], [");
            }
        }
        System.out.println("]");
    }


    public String getID() {
        return this.id;
    }
    public String getGender() {
        return this.gender;
    }
    public String getSpecial_information() {
        return special_information;
    }
    public String getReligion() {
        return religion;
    }
    public double getPresence_weight() {
        return presence_weight;
    }
    public double getOther_presence() {
        return other_presence;
    }
    public double getSelf_presence() {
        return self_presence;
    }
    public double getExtroversion_weight() {
        return extroversion_weight;
    }
    public double getOther_extroversion() {
        return other_extroversion;
    }
    public double getSelf_extroversion() {
        return self_extroversion;
    }
    public double getSleep_weight() {
        return sleep_weight;
    }
    public String getSleep() {
        return sleep;
    }
    public double getHangout_weight() {
        return hangout_weight;
    }
    public double getHangout() {
        return hangout;
    }
    public double getGuests_weight() {
        return guests_weight;
    }
    public double getOther_guests() {
        return other_guests;
    }
    public double getSelf_guests() {
        return self_guests;
    }
    public double getCleanliness_weight() {
        return cleanliness_weight;
    }
    public double getOther_cleanliness() {
        return other_cleanliness;
    }
    public double getSelf_cleanliness() {
        return self_cleanliness;
    }
    public double getNumber_of_roommates() {
        return number_of_roommates;
    }
    public String getName() {
        return name;
    }
    public String[] getPreferred_group_size() {
        return this.preferred_group_size;
    }

    public static List<StudentPreference> getPreference_list() {
        return preference_list;
    }

    public String getLowest_possible_score() {
        return this.lowest_possible_score;
    }
    public int getGroupID() {
        return groupID;
    }
}
