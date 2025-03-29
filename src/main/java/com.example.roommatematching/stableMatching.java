package com.example.roommatematching;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class stableMatching {

    private final List<Group> allGroups;
    private final List<Student> malePairSeekers;
    private final List<Student> femalePairSeekers;
    private final List<Student> maleGroupSeekers;
    private final List<Student> femaleGroupSeekers;
    private final List<Student> malePairBackups;
    private final List<Student> femalePairBackups;
    private final List<Student> maleGroupBackups;
    private final List<Student> femaleGroupBackups;
    private final List<Student> maleOddMenOut = new ArrayList<>();
    private final List<Student> femaleOddMenOut = new ArrayList<>();

    // COLORS
    static final String RESET = "\u001B[0m";
    static final String GREEN = "\u001B[32m";
    static final String RED = "\u001B[31m";
    static final String YELLOW = "\u001B[33m";
    static final String PURPLE = "\u001B[35m";

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

    // Start matching process
    public void matchStudents() {
        // Begin initial Matching
        // Step 1: Match Group Seekers - Complete
        matchGroupSeekers(maleGroupSeekers, "Male");
        matchGroupSeekers(femaleGroupSeekers, "Female");

        // Step 2: Match Pair Seekers - Complete
         matchPairSeekers(malePairSeekers, "Male");
         matchPairSeekers(femalePairSeekers, "Female");

        // Step 3: Handle Backup Lists - In Progress
        matchBackups(maleGroupBackups, malePairBackups, "Male");
        matchBackups(femaleGroupBackups, femalePairBackups, "Female");

        // Step 4: Output Results
        reassignGroupIDs(allGroups);
        outputResults();

        // Output compatability for Groups
        analyzeGroupCompatibility(allGroups);
        printOddManOut("Male");
        printOddManOut("Female");
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
        // Debug Statement
        printLeftoverGroups("GroupSeekers",4, gender);
    }

    // Step 2: Match Pair Seekers to Pairs
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
        // printCurrentGroups();
        // System.out.println("Starting backups...\n");

        // remove any students from Backups if they are fully matched with their primary choice.
        removeMatchedBackups(groupBackups, pairBackups);
        // printBackupLists(groupBackups, pairBackups, gender);

        // Start filling in Groups with backups.
        matchWithGroupBackups(groupBackups, gender);

        // Print any groups that are still not full after placing backups
        printLeftoverGroups("pairBackups", 4, gender);

        // If there are unfulfilled group(s), (i.e. groupBackups ran out) try to break them into pairs.
        matchWithPairBackups(pairBackups, groupBackups, gender);
    }

    // Helper method to handle first part of backup matches
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
                if (gender.equals("Male")) {
                    maleOddMenOut.add(student);
                } else {
                    femaleOddMenOut.add(student);
                }

            }
        }
    }

    // Helper method to handle second part of backup matches
    private void matchWithPairBackups(List<Student> groupBackups, List<Student> pairBackups, String gender) {
        removeMatchedBackups(groupBackups, pairBackups);

        // Match broken groups into pairs
        Iterator<Student> pairIterator = groupBackups.iterator();
        while (pairIterator.hasNext()) {
            Student student = pairIterator.next();

            for (Group group : allGroups) {
                if (group.getMaxSize() == 2 && !group.isFull() && group.getGender().equals(gender)) {
                    Iterator<Group> groupIterator = allGroups.iterator();
                    while (groupIterator.hasNext()) {
                        Group originalGroup = groupIterator.next();
                        if (originalGroup.contains(student)) {
                            originalGroup.removeStudent(student);
                            if (originalGroup.getSize() == 0) {
                                groupIterator.remove();
                            }
                            break;
                        }
                    }
                    group.addStudent(student);
                    pairIterator.remove(); // ✅ Safe removal
                    break;
                }
            }
        }
        // Always run cleanup once at the end to check if any unfilled groups still remain.
        cleanupLeftoverGroups(gender);
    }

    private void cleanupLeftoverGroups(String gender) {
        Iterator<Group> cleanupIterator = allGroups.iterator();
        while (cleanupIterator.hasNext()) {
            Group group = cleanupIterator.next();
            if (!group.isFull() && group.getGender().equals(gender)) {
                for (Student stud : group.getActualStudents()) {
                    System.out.println("Removing "+stud.getName()+" from leftover group.");
                    if (!isStudentInFullGroup(stud)) {
                        if (gender.equals("Male")) {
                            maleOddMenOut.add(stud);
                        } else {
                            femaleOddMenOut.add(stud);
                        }
                    }
                }
                System.out.println("Removing leftover Group: "+group.getGroupID());
                cleanupIterator.remove();
            }
        }
    }

    // prints the final students who were left out.
    private void printOddManOut(String gender) {
        System.out.println("\nOdd Students Out: ("+ gender +")");
        if (gender.equals("Male")) {
            if (maleOddMenOut.isEmpty()) {
                System.out.println(GREEN+"All students matched!"+RESET);
            } else {
                for (Student odd : maleOddMenOut) {
                    System.out.println(RED+odd.getID() + " - " + odd.getName()+RESET);
                }
            }
        } else {
            if (femaleOddMenOut.isEmpty()) {
                System.out.println(GREEN+"All students matched!"+RESET);
            } else {
                for (Student odd : femaleOddMenOut) {
                    System.out.println(RED+odd.getID() + " - " + odd.getName()+RESET);
                }
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

    // Helper method to reduce duplicate logic
    private static void analyzeGroupCompatibility(List<Group> allGroups) {
        for (Group group : allGroups) {
            if (group.isFull()) {
                System.out.println(PURPLE+"\n\nGroup " + group.getGroupID() + " Compatibility:"+RESET);
                List<Student> members = group.getActualStudents();

                double totalScore = 0;
                int comparisons = 0;

                for (int i = 0; i < members.size(); i++) {
                    Student a = members.get(i);
                    if (isPhantomStudent(a)) continue;
                    for (int j = i + 1; j < members.size(); j++) {
                        Student b = members.get(j);
                        if (isPhantomStudent(b)) continue;
                        double score = calculateCompatibilityScore(a, b);
                        totalScore += score;
                        comparisons++;

                        System.out.println(a.getName() + " & " + b.getName() + ": "+ score);
                    }
                }
                double averageScore = (comparisons > 0) ? totalScore / comparisons : 0;

                System.out.printf(" => Overall Group Compatibility: %.2f\n", averageScore);
            }
        }
    }

    private static double calculateCompatibilityScore(Student a, Student b) {
        StudentPreference preference = new StudentPreference(a, b);

        return preference.match_score;
    }

    private static boolean isPhantomStudent(Student s) {
        return s.getID().matches(".*_\\d+$");
    }

    private void reassignGroupIDs(List<Group> allGroups) {
        for (int i = 0; i < allGroups.size(); i++) {
            allGroups.get(i).setGroupID(i + 1);  // Make IDs start from 1
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
                System.out.println("Group " + group.getGroupID() + ": " + group.getStudents() + " - Size: " + group.getSize() + "/" + group.getMaxSize());
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
