package com.example.roommatematching;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private int size;
    private int maxSize;
    private String gender;
    private int groupID;
    private List<Student> students_in_group_list;

    // Constructor
    public Group(int groupID, int maxSize, String gender) {
        this.groupID = groupID;
        this.students_in_group_list = new ArrayList<>();
        this.maxSize = maxSize;
        this.size = 0; // Initially no students
        this.gender = gender;
    }

    // Get the size of the group
    public int getSize() {
        return students_in_group_list.size();
    }

    public String getGender() {
        return this.gender;
    }

    // Check if a student is in this group
    public boolean contains(Student student) {
        return students_in_group_list.contains(student);
    }

    // Get the group ID
    public int getGroupID() {
        return this.groupID;
    }

    // Add a student to the group
    public void addStudent(Student student) {
        students_in_group_list.add(student);
        size += 1; // Add first student.
        // Check if they have any roommates already.
        int numRoommates = (int) student.getNumber_of_roommates();
        for (int i = 1; i <= numRoommates; i++) {
            Student roommate = new Student();
            roommate.setName(student.getName() + "'s Roommate");
            roommate.setID(student.getID() + "_" + i);
            students_in_group_list.add(roommate);
            size += 1; // Increment size each time a student is added
        }
    }



    // Removes all student from the group
    public void removeallStudents() {
        students_in_group_list.clear();
    }

    public void removeStudent(Student student) {
        students_in_group_list.remove(student);
    }

    // Find a student in the group
    public int findStudent(Student student) {
        return students_in_group_list.indexOf(student);
    }

    // Optionally, a method to check if the group is full
    public boolean isFull() {
        return students_in_group_list.size() >= this.maxSize;
    }

    // Get the list of students in this group
    public ArrayList<Object> getStudents() {
        ArrayList<Object> students = new ArrayList<>();
        String id;
        for (Student student : this.students_in_group_list) {
            id = student.getID();
            students.add(id);
        }
        return students;
    }

    public List<Student> getActualStudents() {
        return this.students_in_group_list;
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public void setGroupID(int i) {
        this.groupID = i;
    }
}
