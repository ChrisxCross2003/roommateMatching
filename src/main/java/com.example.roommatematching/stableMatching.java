package com.example.roommatematching;

import java.util.ArrayList;
import java.util.List;

public class stableMatching {

    private final int MAX_GROUP_SIZE = 4;
    private List<Group> allGroups;
    private List<Student> malePairSeekers;
    private List<Student> femalePairSeekers;
    private List<Student> maleGroupSeekers;
    private List<Student> femaleGroupSeekers;
    private List<Student> malePairBackups;
    private List<Student> femalePairBackups;
    private List<Student> maleGroupBackups;
    private List<Student> femaleGroupBackups;

    // Constructor
    public stableMatching(List<Student> malePairSeekers, List<Student> femalePairSeekers,
                          List<Student> maleGroupSeekers, List<Student> femaleGroupSeekers,
                          List<Student> malePairBackups, List<Student> femalePairBackups,
                          List<Student> maleGroupBackups, List<Student> femaleGroupBackups) {
        this.malePairSeekers = malePairSeekers;
        this.femalePairSeekers = femalePairSeekers;
        this.maleGroupSeekers = maleGroupSeekers;
        this.femaleGroupSeekers = femaleGroupSeekers;
        this.malePairBackups = malePairBackups;
        this.femalePairBackups = femalePairBackups;
        this.maleGroupBackups = maleGroupBackups;
        this.femaleGroupBackups = femaleGroupBackups;
        this.allGroups = new ArrayList<>();
    }

    // Start the matching process
    public void matchStudents() {
        // Step 1: Match Group Seekers
        matchGroupSeekers(maleGroupSeekers, "Male");
        matchGroupSeekers(femaleGroupSeekers, "Female");

        // Step 2: Match Pair Seekers
        matchPairSeekers(malePairSeekers, "Male");
        matchPairSeekers(femalePairSeekers, "Female");

        // Step 3: Handle Backup Lists
        handleBackups(malePairBackups, "Male", "Pair");
        handleBackups(femalePairBackups, "Female", "Pair");
        handleBackups(maleGroupBackups, "Male", "Group");
        handleBackups(femaleGroupBackups, "Female", "Group");

        // Step 4: Final Check - Ensure no group exceeds max size
        checkGroupOverflow();

        // Step 5: Output Results (This can be improved to suit the format you want)
        outputResults();
    }

    // Step 1: Match Group Seekers to Groups
    private void matchGroupSeekers(List<Student> groupSeekers, String gender) {
        for (Student student : groupSeekers) {
            boolean matched = false;

            // Try matching with existing groups
            for (Group group : allGroups) {
                if (!group.isFull(MAX_GROUP_SIZE) && !group.contains(student)) {
                    group.addStudent(student);
                    matched = true;
                    break;
                }
            }

            // If no match found, create a new group
            if (!matched) {
                Group newGroup = new Group(allGroups.size() + 1);
                newGroup.addStudent(student);
                allGroups.add(newGroup);
            }
        }
    }

    // Step 2: Match Pair Seekers (Add to existing groups or create new groups)
    private void matchPairSeekers(List<Student> pairSeekers, String gender) {
        for (Student student : pairSeekers) {
            boolean matched = false;

            // Try matching with existing groups (for pair seekers, can match with any available spot)
            for (Group group : allGroups) {
                if (!group.isFull(MAX_GROUP_SIZE) && !group.contains(student)) {
                    group.addStudent(student);
                    matched = true;
                    break;
                }
            }

            // If no match found, create a new group
            if (!matched) {
                Group newGroup = new Group(allGroups.size() + 1);
                newGroup.addStudent(student);
                allGroups.add(newGroup);
            }
        }
    }

    // Step 3: Handle Backup Lists (For students who couldn't be placed initially)
    private void handleBackups(List<Student> backups, String gender, String type) {
        for (Student student : backups) {
            boolean matched = false;

            // Try matching with existing groups
            for (Group group : allGroups) {
                if (!group.isFull(MAX_GROUP_SIZE) && !group.contains(student)) {
                    group.addStudent(student);
                    matched = true;
                    break;
                }
            }

            // If no match found, create a new group
            if (!matched) {
                Group newGroup = new Group(allGroups.size() + 1);
                newGroup.addStudent(student);
                allGroups.add(newGroup);
            }
        }
    }

    // Step 4: Final Check - Ensure no group exceeds max size
    private void checkGroupOverflow() {
        for (Group group : allGroups) {
            if (group.getSize() > MAX_GROUP_SIZE) {
                // Handle overflow by splitting or other methods
                // You could introduce additional logic here
            }
        }
    }

    // Step 5: Output Results (for demonstration purposes)
    private void outputResults() {
        System.out.println("Total Groups: " + allGroups.size());
        for (Group group : allGroups) {
            System.out.println("Group " + group.getGroupID() + ": " + group.getStudents());
        }
    }
}
