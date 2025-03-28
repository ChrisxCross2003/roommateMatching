package com.example.roommatematching;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private int size;
    private int groupID;
    private List<Student> students_in_group_list;

    // Constructor
    public Group(int groupID) {
        this.groupID = groupID;
        this.students_in_group_list = new ArrayList<>();
        this.size = 0; // Initially no students
    }

    // Get the size of the group
    public int getSize() {
        return students_in_group_list.size();
    }

    // Set the group ID
    public void setGroupID(int id) {
        this.groupID = id;
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
        size += 1; // Increment size each time a student is added
    }

    // Remove a student from the group
    public void removeStudent(Student student) {
        if (students_in_group_list.contains(student)) {
            students_in_group_list.remove(student);
            size -= 1; // Decrement size when a student is removed
        }
    }

    // Find a student in the group
    public int findStudent(Student student) {
        return students_in_group_list.indexOf(student);
    }

    // Optionally, a method to check if the group is full
    public boolean isFull(int maxGroupSize) {
        return students_in_group_list.size() >= maxGroupSize;
    }

    // Get the list of students in this group
    public List<Student> getStudents() {
        return students_in_group_list;
    }
}
