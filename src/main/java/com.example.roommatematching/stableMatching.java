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

    // COLORS
    final String CYAN = "\u001B[36m";
    final String RESET = "\u001B[0m";
    final String GREEN = "\u001B[32m";
    final String RED = "\u001B[31m";
    final String YELLOW = "\u001B[33m";
    final String BLUE = "\u001B[34m";
    final String PURPLE = "\u001B[35m";

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
        for (Student student : groupSeekers) {
            boolean matched = false;

            // Try matching with existing groups
            for (Group group : allGroups) {
                if (!group.isFull() && !group.contains(student) && group.getGender().equals(gender)) {
                    // System.out.println("Group found!");
                    // System.out.println("Adding " + student.getName() + " to group " + group.getGroupID());
                    group.addStudent(student);
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                Group newGroup = new Group(allGroups.size() + 1, 4, gender);
                newGroup.addStudent(student);
                allGroups.add(newGroup);
                // System.out.println("No group found.");
                // System.out.println("Adding " + student.getName() + " to group " + newGroup.getGroupID());
            }
        }
        // Debug Statment
        printLeftoverGroups("GroupSeekers",4, gender);
    }



    private void matchPairSeekers(List<Student> pairSeekers, String gender) {
        for (Student student : pairSeekers) {
            boolean matched = false;

            // Only consider groups meant for pairs (maxSize == 2)
            for (Group group : allGroups) {
                if (group.getMaxSize() == 2 && !group.isFull() && group.getGender().equals(gender)) {
                    // System.out.println("Pair Found!.");
                    // System.out.println("Adding " + student.getName() + " to group " + group.getGroupID());
                    group.addStudent(student);
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                Group pairGroup = new Group(allGroups.size() + 1, 2, gender);  // max size 2 = pair
                pairGroup.addStudent(student);
                // System.out.println("No pair found.");
                // System.out.println("Adding " + student.getName() + " to group " + pairGroup.getGroupID());
                allGroups.add(pairGroup);
            }
        }
        // Debug Statement
        printLeftoverGroups("PairSeekers",2, gender);
    }

    // Step 3: Handle Backup Lists (For students who couldn't be placed initially)
    private void matchBackups(List<Student> groupBackups, List<Student> pairBackups, String gender) {
        //printCurrentGroups();
        //System.out.println("Starting backups...\n");

        // remove any students from Backups if they are fully matched with their primary choice.
        removeMatchedBackups(groupBackups, pairBackups);
        //printBackupLists(groupBackups, pairBackups, gender);

        // Start filling in Groups with backups.
        matchWithGroupBackups(pairBackups, gender);

        // Print any groups that are still not full after placing backups
        printLeftoverGroups("pairBackups", 4, gender);

        // If there are unfulfilled group(s), (i.e. groupBackups ran out) try to break them into pairs.
        matchWithPairBackups(groupBackups, pairBackups, gender);
        printOddManOut(gender);
    }

    private void matchWithGroupBackups(List<Student> pairBackups, String gender) {
        Iterator<Student> iterator = pairBackups.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            boolean matched = false;

            for (Group group : allGroups) {
                // add backup if compatible and delete their original group.
                if (group.getMaxSize() == 4 && !group.contains(student) && !group.isFull() && group.getGender().equals(gender)) {
                    //System.out.println("Backup for Group found!");
                    //System.out.println("Adding " + student.getName() + " to group " + group.getGroupID());

                    // Iterator to remove a backup's original group if they were matched.
                    Iterator<Group> groupIterator = allGroups.iterator();
                    while (groupIterator.hasNext()) {
                        Group originalGroup = groupIterator.next();
                        if (originalGroup.contains(student)) {
                            //System.out.println("Deleting original group...");
                            groupIterator.remove(); // Remove the group from allGroups
                            break;
                        }
                    }
                    group.addStudent(student);
                    iterator.remove();
                    matched = true;
                    break;
                }
            }

            if (!matched && !isStudentInFullGroup(student)) {
                oddMenOut.add(student);
            }
        }
    }

    private void matchWithPairBackups(List<Student> groupBackups, List<Student> pairBackups, String gender) {
        List<Student> remainingFromGroups = new ArrayList<>();
        removeMatchedBackups(groupBackups, pairBackups);

        for (Group group : allGroups) {
            if (group.getMaxSize() == 4 && !group.isFull() && group.getGender().equals(gender)) {
                remainingFromGroups.addAll(group.getActualStudents());
                group.removeallStudents(); // break up the group.
            }
        }

        // Combine remaining group members and pair backups
        groupBackups.addAll(remainingFromGroups);

        // Match broken groups into pairs
        Iterator<Student> pairIterator = groupBackups.iterator();
        while (pairIterator.hasNext()) {
            Student student = pairIterator.next();
            boolean matched = false;

            for (Group group : allGroups) {
                if (group.getMaxSize() == 2 && !group.isFull() && group.getGender().equals(gender)) {
                    //System.out.println("Backup for Pair found!");
                    //System.out.println("Adding " + student.getName() + " to group " + group.getGroupID());

                    // if backup student found, remove them from original group.
                    Iterator<Group> groupIterator = allGroups.iterator();
                    while (groupIterator.hasNext()) {
                        Group originalGroup = groupIterator.next();
                        if (originalGroup.contains(student)) {
                            //System.out.println("Removing student from group...");
                            originalGroup.removeStudent(student);
                            if (originalGroup.getSize() == 0) {
                                // if original group is now empty, delete it.
                                //System.out.println("Group is empty. Deleting Group...");
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
    }

    // prints the final students who were left out.
    private void printOddManOut(String gender) {
        System.out.println("\nOdd Students Out: ("+ gender +")");
        if (oddMenOut.isEmpty()) {
            System.out.println(GREEN+"All students matched!"+RESET);
        } else {
            for (Student odd : oddMenOut) {
                System.out.println(RED+odd.getid() + " - " + odd.getName()+RESET);
            }
        }
    }

    // checks the backup lists to see if any backups were added to a full group. If so, remove them.
    private void removeMatchedBackups(List<Student> groupBackups, List<Student> pairBackups) {
        groupBackups.removeIf(this::isStudentInFullGroup);
        pairBackups.removeIf(this::isStudentInFullGroup);
    }

    // Helper Method to check if student is in a full group.
    private boolean isStudentInFullGroup(Student student) {
        for (Group group : allGroups) {
            if (group.contains(student) && group.isFull()) {
                return true;
            }
        }
        return false;
    }

    // Output Results (for demonstration purposes)
    private void outputResults() {
        System.out.println(GREEN+"\nTotal Groups: " + allGroups.size()+RESET);
        for (Group group : allGroups) {
            System.out.println("Group " + group.getGroupID() + ": " + group.getStudents() + " - Size: " + group.getSize());
        }
    }


    /*
    / DEBUG helper methods below:
     */

    // prints leftover groups from initial matches.
    private void printLeftoverGroups(String groupType, int groupSize, String gender) {
        System.out.println(YELLOW+"\nLeftover groups from "+groupType+" matching: (" + gender + ")"+RESET);
        for (Group group : allGroups) {
            if (group.getMaxSize() == groupSize && !group.isFull() && group.getGender().equals(gender)) {
                System.out.println("Group " + group.getGroupID() + ": " + group.getStudents() + " - Size: " + group.getSize() + "/" + group.getMaxSize()+"\n");
            }
        }
    }

    // prints the current groups during runtime.
    private void printCurrentGroups() {
        System.out.println("\nCurrent Groups: ");
        for (Group group : allGroups) {
            System.out.println("Group " + group.getGroupID() + ": " + group.getStudents() + " - Size: " + group.getSize() + "/" + group.getMaxSize());
        }
    }

    private void printBackupLists(List<Student> groupBackups, List<Student> pairBackups, String gender) {
        System.out.println(YELLOW+"Students in pairBackups for "+ gender +"s"+RESET);
        for (Student student : groupBackups) {
            System.out.println(student.getName());
        }

        System.out.println(YELLOW+"\nStudents in groupBackups for "+ gender +"s"+RESET);
        for (Student student : pairBackups) {
            System.out.println(student.getName());
        }
    }
}
