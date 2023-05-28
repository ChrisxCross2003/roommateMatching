package com.example.roommatematching;

import java.util.Objects;

public class StudentPreference {
    public String match_score;
    public String computing_id;
    public String[] preferred_roommates;
    public double number_of_roommates;

    public StudentPreference (Student student1, Student student2) {
        this.preferred_roommates = student2.getPreferred_group_size();
        this.number_of_roommates = student2.getNumber_of_roommates();
        calculate_preference(student1, student2);
    }

    public void calculate_preference(Student student1, Student student2) {
        String final_score;
        double score;

        double clean_score = Math.abs((student1.getOther_cleanliness() - student2.getSelf_cleanliness()) * student1.getCleanliness_weight());
        double guests_score = Math.abs((student1.getOther_guests() - student2.getSelf_guests()) * student1.getGuests_weight());
        double hangout_score = Math.abs((student1.getHangout() - student2.getHangout()) * student1.getHangout_weight());
        double sleep_score = 0;
        if (!Objects.equals(student1.getSleep(), student2.getSleep())) sleep_score = student1.getSleep_weight();
        double extroversion_score = Math.abs((student1.getOther_extroversion() - student2.getSelf_extroversion()) * student1.getExtroversion_weight());
        double presence_score = Math.abs((student1.getOther_presence() - student2.getSelf_presence()) * student1.getPresence_weight());

        score = clean_score + guests_score + hangout_score + sleep_score + extroversion_score + presence_score;
        if (score == 0) {
            if (!Objects.equals(student2.getReligion(), "") || !Objects.equals(student2.getSpecial_information(), "") || student2.getSpecial_information() != null || student2.getReligion() != null) {
                final_score = String.valueOf(100);
            } else {
                final_score = 100 +"*";
            }
        } else {
            int worst_case_score = 105;
            if (!Objects.equals(student2.getReligion(), "") || !Objects.equals(student2.getSpecial_information(), "")) {
                final_score = (100 - (score/worst_case_score))+"*";
            } else {
                final_score = String.valueOf((100 - (score/worst_case_score)));
            }
        }
        this.match_score = final_score;
        this.computing_id = student2.getid();
    }
}
