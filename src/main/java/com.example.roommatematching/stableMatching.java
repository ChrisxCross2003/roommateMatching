package com.example.roommatematching;

import java.util.ArrayList;
import java.util.List;

public class stableMatching {

    private List<Group> allGroups;
    private List<Student> malePairSeekers;
    private List<Student> femalePairSeekers;
    private List<Student> maleGroupSeekers;
    private List<Student> femaleGroupSeekers;
    private List<Student> malePairBackups;
    private List<Student> femalePairBackups;
    private List<Student> maleGroupBackups;
    private List<Student> femaleGroupBackups;
    private List<Student> oddMenOut = new ArrayList<>();

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
        // Step 1: Match Group Seekers - Complete
        matchGroupSeekers(maleGroupSeekers, "Male");
        matchGroupSeekers(femaleGroupSeekers, "Female");

        // Step 2: Match Pair Seekers - Complete
         matchPairSeekers(malePairSeekers, "Male");
         matchPairSeekers(femalePairSeekers, "Female");

        // Step 3: Handle Backup Lists - In Progress
        matchBackups(maleGroupSeekers, malePairSeekers);
        matchBackups(femaleGroupSeekers, femalePairSeekers);

        // Step 4: Output Results
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
                if (!group.isFull() && !group.contains(student)) {
                    group.addStudent(student);
                    matched = true;
                    break;
                }
            }

            // Base case: If no match found, create a new group.
            if (!matched) {
                Group newGroup = new Group(allGroups.size() + 1, 4);
                newGroup.addStudent(student);
                allGroups.add(newGroup);
            }
        }
    }

    private void matchPairSeekers(List<Student> pairSeekers, String gender) {
        for (Student student : pairSeekers) {
            boolean matched = false;

            // Only consider groups meant for pairs (maxSize == 2)
            for (Group group : allGroups) {
                if (group.getMaxSize() == 2 && !group.isFull() && !group.contains(student)) {
                    group.addStudent(student);
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                Group pairGroup = new Group(allGroups.size() + 1, 2);  // max size 2 = pair
                pairGroup.addStudent(student);
                allGroups.add(pairGroup);
            }
        }
    }

    // Step 3: Handle Backup Lists (For students who couldn't be placed initially)
    private void matchBackups(List<Student> groupBackups, List<Student> pairBackups) {
        // remove any students from Backups if they are fully matched with their primary choice.
        groupBackups.removeIf(this::isStudentInFullGroup);
        pairBackups.removeIf(this::isStudentInFullGroup);

        // Start filling in Groups with backups.
        for (Student student : groupBackups) {
            boolean matched = false;

            // Try adding to an existing group (if not full)
            for (Group group : allGroups) {
                if (group.getMaxSize() == 4 && !group.isFull()) {
                    group.addStudent(student);
                    groupBackups.remove(student); // remove student from backups list.
                    matched = true;
                    break;
                }
            }

            // If no match, they're odd man out. No groups exist, so primary and secondary matches didn't work.
            if (!matched) {
                oddMenOut.add(student); // add student to odd man out.
            }
        }

        // Print any groups that are still not full after placing backups
        System.out.println("\n---- Unfilled Groups After Group Backups ----");
        boolean hasUnfilled = false;
        for (Group group : allGroups) {
            if (group.getMaxSize() == 4 && !group.isFull()) {
                System.out.println("Group " + group.getGroupID() + ": " + group.getStudents() + " - Size: " + group.getSize() + "/" + group.getMaxSize());
                hasUnfilled = true;
            }
        }
        if (!hasUnfilled) {
            System.out.println("All 4-person groups are now full.");
        }

        // If there are unfulfilled group(s), (i.e. groupBackups ran out) try to break them into pairs.

        // check which groups can break off. If so, remove one person and add them with the pair.
            // if no pairs remain, but still backups, add them to the odd-man-out-list.
            // if one unsettled pair remains, but no backups left, add them to the odd-man-out-list.

        // run a final check on odd-man out list. Can the odd-men-out match?
            // if so, add them to a group and remove them from the list.
            // if not, whoever remains can not be matched.

        // Print any groups that are still not full after placing backups
        System.out.println("\n---- Unfilled Groups After Final Backup Check ----");
        for (Group group : allGroups) {
            if (group.getMaxSize() == 4 && !group.isFull()) {
                System.out.println("Group " + group.getGroupID() + ": " + group.getStudents() + " - Size: " + group.getSize() + "/" + group.getMaxSize());
            }
        }

    }

    // Step 4: Final Check - Ensure no group exceeds max size
    private boolean isStudentInFullGroup(Student student) {
        for (Group group : allGroups) {
            if (group.contains(student) && group.isFull()) {
                return true;
            }
        }
        return false;
    }

    // Step 5: Output Results (for demonstration purposes)
    private void outputResults() {
        System.out.println("Total Groups: " + allGroups.size());
        for (Group group : allGroups) {
            System.out.println("Group " + group.getGroupID() + ": " + group.getStudents() + " - Size: " + group.getSize());
        }
    }
}
