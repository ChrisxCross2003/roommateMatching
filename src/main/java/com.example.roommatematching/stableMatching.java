package com.example.roommatematching;

import java.util.ArrayList;
import java.util.Iterator;
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
        matchBackups(maleGroupSeekers, malePairSeekers, "Male");
        matchBackups(femaleGroupSeekers, femalePairSeekers, "Female");

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
                if (!group.isFull() && !group.contains(student) && group.getGender().equals(gender)) {
                    System.out.println("Group found!");
                    System.out.println("Adding " + student.getName() + " to group " + group.getGroupID());
                    group.addStudent(student);
                    matched = true;
                    break;
                }
            }

            // Base case: If no match found, create a new group.
            if (!matched) {
                Group newGroup = new Group(allGroups.size() + 1, 4, gender);
                newGroup.addStudent(student);
                allGroups.add(newGroup);
                System.out.println("No group found.");
                System.out.println("Adding " + student.getName() + " to group " + newGroup.getGroupID());
            }
        }
        System.out.println("\n\nUnfilled Groups after initial match: (" + gender + ")");
        for (Group group : allGroups) {
            if (group.getMaxSize() == 4 && !group.isFull() && group.getGender().equals(gender)) {
                System.out.println("Group " + group.getGroupID() + ": " + group.getStudents() + " - Size: " + group.getSize() + "/" + group.getMaxSize()+"\n");
            }
        }
    }

    private void matchPairSeekers(List<Student> pairSeekers, String gender) {
        for (Student student : pairSeekers) {
            boolean matched = false;

            // Only consider groups meant for pairs (maxSize == 2)
            for (Group group : allGroups) {
                if (group.getMaxSize() == 2 && !group.isFull() && group.getGender().equals(gender)) {
                    System.out.println("Pair Found!.");
                    System.out.println("Adding " + student.getName() + " to group " + group.getGroupID());
                    group.addStudent(student);
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                Group pairGroup = new Group(allGroups.size() + 1, 2, gender);  // max size 2 = pair
                pairGroup.addStudent(student);
                System.out.println("No pair found.");
                System.out.println("Adding " + student.getName() + " to group " + pairGroup.getGroupID());
                allGroups.add(pairGroup);
            }
        }
        System.out.println("\n\nUnfilled Pairs after initial match: (" + gender + ")");
        for (Group group : allGroups) {
            if (group.getMaxSize() == 2 && !group.isFull() && group.getGender().equals(gender)) {
                System.out.println("Group " + group.getGroupID() + ": " + group.getStudents() + " - Size: " + group.getSize() + "/" + group.getMaxSize()+"\n");
            }
        }
    }

    // Step 3: Handle Backup Lists (For students who couldn't be placed initially)
    private void matchBackups(List<Student> groupBackups, List<Student> pairBackups, String gender) {
        System.out.println("\nCurrent Groups: ");
        for (Group group : allGroups) {
            System.out.println("Group " + group.getGroupID() + ": " + group.getStudents() + " - Size: " + group.getSize() + "/" + group.getMaxSize());
        }
        System.out.println("Starting backups...\n");
        // remove any students from Backups if they are fully matched with their primary choice.
        groupBackups.removeIf(this::isStudentInFullGroup);
        pairBackups.removeIf(this::isStudentInFullGroup);

        System.out.println("Students in pairBackups for "+gender+"s");
        for (Student student : groupBackups) {
            System.out.println(student.getName());
        }

        System.out.println("\nStudents in groupBackups for "+gender+"s");
        for (Student student : pairBackups) {
            System.out.println(student.getName());
        }

        Iterator<Student> iterator = pairBackups.iterator();

        // Start filling in Groups with backups.
        while (iterator.hasNext()) {
            Student student = iterator.next();
            boolean matched = false;

            for (Group group : allGroups) {
                if (group.getMaxSize() == 4 && !group.contains(student) && !group.isFull() && group.getGender().equals(gender)) {
                    System.out.println("Backup for Group found!");
                    System.out.println("Adding " + student.getName() + " to group " + group.getGroupID());

                    // Safe removal: use an iterator to find and remove the original group
                    Iterator<Group> groupIterator = allGroups.iterator();
                    while (groupIterator.hasNext()) {
                        Group originalGroup = groupIterator.next();
                        if (originalGroup.contains(student)) {
                            System.out.println("Deleting original group...");
                            groupIterator.remove(); // Remove the group from allGroups
                            break;
                        }
                    }
                    // add the student to the new group.
                    group.addStudent(student);
                    iterator.remove(); // ✅ Safe removal
                    matched = true;
                    break;
                }
            }

            if (!matched && !isStudentInFullGroup(student)) {
                oddMenOut.add(student);
            }
        }

        // Print any groups that are still not full after placing backups
        System.out.println("\n---- Unfilled Groups After Group Backups ----: ("+gender+")");
        boolean hasUnfilled = false;
        for (Group group : allGroups) {
            if (group.getMaxSize() == 4 && !group.isFull() && group.getGender().equals(gender)) {
                System.out.println("Group " + group.getGroupID() + ": " + group.getStudents() + " - Size: " + group.getSize() + "/" + group.getMaxSize());
                hasUnfilled = true;
            }
        }
        if (!hasUnfilled) {
            System.out.println("All 4-person groups are now full.");
        }

        // If there are unfulfilled group(s), (i.e. groupBackups ran out) try to break them into pairs.
        List<Student> remainingFromGroups = new ArrayList<>();

        // remove any students from Backups if they are fully matched with their primary choice.
        groupBackups.removeIf(this::isStudentInFullGroup);
        pairBackups.removeIf(this::isStudentInFullGroup);

        for (Group group : allGroups) {
            if (group.getMaxSize() == 4 && !group.isFull() && group.getGender().equals(gender)) {
                remainingFromGroups.addAll(group.getActualStudents());
                group.removeallStudents(); // break up the group.
            }
        }

        // Combine remaining group members and pair backups
        groupBackups.addAll(remainingFromGroups);
        Iterator<Student> pairIterator = groupBackups.iterator();

        // Match into pairs
        while (pairIterator.hasNext()) {
            Student student = pairIterator.next();
            boolean matched = false;

            for (Group group : allGroups) {
                if (group.getMaxSize() == 2 && !group.isFull() && group.getGender().equals(gender)) {
                    System.out.println("Backup for Pair found!");
                    System.out.println("Adding " + student.getName() + " to group " + group.getGroupID());

                    // Safe removal: use an iterator to find and remove the original group
                    Iterator<Group> groupIterator = allGroups.iterator();
                    while (groupIterator.hasNext()) {
                        Group originalGroup = groupIterator.next();
                        if (originalGroup.contains(student)) {
                            System.out.println("Removing student from group...");
                            originalGroup.removeStudent(student);
                            if (originalGroup.getSize() == 0) {
                                System.out.println("Group is empty. Deleting Group...");
                                groupIterator.remove(); // Remove the group from allGroups
                            }
                            break;
                        }
                    }
                    group.addStudent(student);
                    pairIterator.remove(); // ✅ Safe removal
                    matched = true;
                    break;
                }
            }

            if (!matched && !isStudentInFullGroup(student)) {
                oddMenOut.add(student);
            }
        }

        // Print final unmatched students
        System.out.println("\n---- Students Left Unmatched (Odd-Men-Out) ----: ("+gender+")");
        if (oddMenOut.isEmpty()) {
            System.out.println("All students matched!");
        } else {
            for (Student odd : oddMenOut) {
                System.out.println(odd.getid() + " - " + odd.getName());
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
