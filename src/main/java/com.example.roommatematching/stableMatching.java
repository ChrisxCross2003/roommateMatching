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
        // matchPairSeekers(malePairSeekers, "Male");
        // matchPairSeekers(femalePairSeekers, "Female");

        // Step 3: Handle Backup Lists
//        handleBackups(malePairBackups, "Male", "Pair");
//        handleBackups(femalePairBackups, "Female", "Pair");
//        handleBackups(maleGroupBackups, "Male", "Group");
//        handleBackups(femaleGroupBackups, "Female", "Group");

        // Step 4: Final Check - Ensure no group exceeds max size
//        checkGroupOverflow();

        // Step 5: Output Results (This can be improved to suit the format you want)
        outputResults();
    }

    // Step 1: Match Group Seekers to Groups
    private void matchGroupSeekers(List<Student> groupSeekers, String gender) {
        // For each student in the groupSeeker list...
            // find their respective highest preference score. then place them in a group together if possible.
            // if they can't be added together because the group would overflow, go to the next best option... and so on.

        // anyone left over will be added as a single to a new group.
        // all unfilled groups will be on standby for next phase of matching...

        // For each student in the groupSeeker list...
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

            // Base case: If no match found, create a new group.
            if (!matched) {
                Group newGroup = new Group(allGroups.size() + 1);
                newGroup.addStudent(student);
                allGroups.add(newGroup);
            }
        }
    }

    // Step 2: Match Pair Seekers (Add to existing groups or create new groups)
    private void matchPairSeekers(List<Student> pairSeekers, String gender) {
        // For each student in the pairSeeker list...
        // find their respective highest preference score. then place them in a pair together if availble.
        // if match is unavailable, move to next highest preference, and so on.

        // anyone left over will be checked if they have a backup, if they do,
        // we put them on standby for the next phase.
        // If they do not have a second preference, they're an odd man out and can't be matched.

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
        // priority goes to Groups to get backups (to get the most amount of people matched.)
        // For each unsettled group, take backups to settle it.
        // if there's not enough backups, any leftover groups that have a backup will be added to pair backup.
        // if there's an odd person, they will be the odd man out.

        // based on this algorithm, odd man outs can only occur if someone doesn't have a backup,
        // or there's an odd number.

        // still need to find a way to ensure each person is as happy as possible with their pair.
        // consider creating a separate method called "optimized_matching" that uses Happy Marriage algorithm
        // to ensure matches best on both sides. (i.e. there's no students in two different groups that have a higher
        // connection to each other than their own group (and is able to swap).
        // This optimization would ensure all students get the best possible match.


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
