package com.example.roommatematching;

import java.util.Objects;

public class StudentPreference {
    public double match_score;
    public String computing_id;
    public String[] preferred_roommates;
    public double number_of_roommates;

    public StudentPreference (Student student1, Student student2) {
        this.preferred_roommates = student2.getPreferred_group_size();
        this.number_of_roommates = student2.getNumber_of_roommates();
        calculate_preference(student1, student2);
    }

    // Calculates the preference score between two students from the perspective of student1
    public void calculate_preference(Student student1, Student student2) {
        double final_score;

        // Get the raw mismatch score between student1's preferences and student2's attributes
        double score = getScore(student1, student2);

        // If there's a perfect match (no mismatch at all), assign a perfect score of 100
        if (score == 0) {
            final_score = 100;
        } else {
            // Define the worst possible mismatch value for normalization (used to calculate a percentage score)
            int worst_case_score = 105;

            // Convert mismatch score to a percentage match score (lower mismatch = higher match)
            final_score = 100.0 - (score / worst_case_score * 100.0);
        }

        // Store the computed match score and the ID of the student being compared
        this.match_score = final_score;
        this.computing_id = student2.getID();
    }

    /**
     * Calculates match compatability between students. Measures the difference between student 1 and student2.
     * The more different, the lower the match score. This also includes weight.
     * For example, if a student finds cleanliness very important (weight = 5), the difference is multiplied.
     */
    private static double getScore(Student student1, Student student2) {
        double score;

        // Calculate mismatch in cleanliness
        double clean_score = Math.abs((student1.getOther_cleanliness() - student2.getSelf_cleanliness()) * student1.getCleanliness_weight());
        // Calculate mismatch in guest tolerance
        double guests_score = Math.abs((student1.getOther_guests() - student2.getSelf_guests()) * student1.getGuests_weight());
        // Calculate mismatch in hangout preferences (how much time they want to spend together)
        double hangout_score = Math.abs((student1.getHangout() - student2.getHangout()) * student1.getHangout_weight());

        // Handle categorical sleep schedules — if they differ, apply the full weight as a small penalty
        double sleep_score = 0;
        if (!Objects.equals(student1.getSleep(), student2.getSleep())) {
            sleep_score = student1.getSleep_weight();
        }

        // Calculate mismatch in extroversion level preferences
        double extroversion_score = Math.abs((student1.getOther_extroversion() - student2.getSelf_extroversion()) * student1.getExtroversion_weight());
        // Calculate mismatch in physical presence preference (e.g., remote vs. present)
        double presence_score = Math.abs((student1.getOther_presence() - student2.getSelf_presence()) * student1.getPresence_weight());

        // Total score is the sum of all weighted mismatches — higher = worse match
        score = clean_score + guests_score + hangout_score + sleep_score + extroversion_score + presence_score;
        return score;
    }
}
