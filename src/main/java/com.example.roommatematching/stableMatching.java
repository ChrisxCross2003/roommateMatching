package com.example.roommatematching;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class stableMatching {
    static List<Student> studentList = new ArrayList<>();
    private static List<Student> freeStudents = new ArrayList<>();
    private static final List<Group> groups = new ArrayList<>();
    public static List<Group> stableRoommateAlgorithm(List<Student> students) {
        studentList = students;
        int numStudents = students.size();
        freeStudents = students;
        for (int i=0;i<numStudents;i++) {
            // for each student, process their proposals
            processProposals(studentList.get(i), Student.getPreference_list());
        }
        // once all proposals have been added once, group students together...
        for (int i=0;i<numStudents;i++) {
            // for each student, check their proposals and accept highest for their group.
            groupStudents(students.get(i), i);
        }
        return groups;
    }

    private static void makeProposal(Student student, Student potentialRoommate, String score) {
        StudentProposal proposal = new StudentProposal(student, score);
        potentialRoommate.addProposer(proposal);
    }
    private static void groupStudents(Student student, int id) {
        Group group = new Group();
        group.setGroupID(id);
        List<StudentProposal> proposalList = student.getProposers();
        int wanted_group_size = 4;

        // get their first choice preference.
        for (int j = 0; j < student.getPreferred_group_size().length; j++) {
            if (Objects.equals(student.getPreferred_group_size()[j], "First Choice")) {
                wanted_group_size = j;
                break;
            }
        }
        int proposal_size = 0;
        // find the largest proposals and add them to group.
        while (group.getSize() < wanted_group_size) {
            double max_proposal = 0;
            // check if proposal is already in a group and if they can be added.
            for (StudentProposal proposal : proposalList) {
                if (proposal.student.getGroupID() != -1) {
                    for (Group g : groups) {
                        if (g.getGroupID() == proposal.student.getGroupID()) {
                            proposal_size = g.getSize();
                            break;
                        }
                    }
                    if (proposal_size + group.getSize() < wanted_group_size) {
                        // if student is in a group, check if adding will NOT overflow the group.
                        max_proposal = findMax(max_proposal, proposal);
                    }
                } else {
                    // if student is not in a group, check if adding them will NOT overflow the group
                    if (proposal.student.getNumber_of_roommates() + group.getSize() < wanted_group_size) {
                        // if no overflow, find max proposal
                        max_proposal = findMax(max_proposal, proposal);
                    }
                }
            }

            for (StudentProposal proposal : proposalList) {
                if (Double.parseDouble(proposal.score) == max_proposal) {
                    // add corresponding proposal-student to group with first student.
                    group.addStudent(proposal.student);
                    // remove preference
                    proposalList.remove(proposal);
                    break;
                }
            }
            groups.add(group);
        }

    }

    private static double findMax(double x, StudentProposal proposal) {
        if (x < Double.parseDouble(proposal.score)) {
            x = Double.parseDouble(proposal.score);
        }
        return x;
    }

    private static void processProposals(Student student, List<StudentPreference> preferences) {
        for (StudentPreference preference : preferences) {
            // For each preference of a student, if both student and their preference want the same amount of roommates,
            String match_score = preference.match_score;
            if (checkIfAbleToPropose(student, preference, match_score)) {
                // begin the process of making a proposal
                for (Student stud : studentList) {
                    if (Objects.equals(stud.getid(), preference.computing_id)) {
                        // find the id of the student from the preference, then propose.
                        makeProposal(student, stud, match_score);
                        break;
                    }
                }
            }
        }
    }
    private static boolean checkIfAbleToPropose(Student student, StudentPreference preference, String match_score) {
        for (int j = 0; j < 3; j++) {
            // check if both groups want the same first choice.
            if (Objects.equals(student.getPreferred_group_size()[j], preference.preferred_roommates[j]) && Objects.equals(student.getPreferred_group_size()[j], "First Choice")) {
                // check if both groups can fit if combined.
                if ((4 - j) - (student.getNumber_of_roommates() + preference.number_of_roommates) >= 0) {
                    // check the minimum number for matching.
                    if (Integer.parseInt(student.getLowest_possible_score()) <= Integer.parseInt(match_score)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
