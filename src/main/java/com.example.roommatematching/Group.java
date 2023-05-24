package com.example.roommatematching;

import java.util.List;

public class Group {
    private int size;
    private int groupID;
    private static List<Student> students_in_group_list;

    private int getSize() {
        return students_in_group_list.size();
    }

    public static void addStudent(Student student) {
        students_in_group_list.add(student);
    }

    public static void removeStudent(Student student) {
        students_in_group_list.remove(student);
    }

    public static int findStudent(Student student) {
        if (students_in_group_list.contains(student)) {
            return students_in_group_list.indexOf(student);
        }
        return -1;
    }
}
