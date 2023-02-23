package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Repository
public class StudentRepository {

    private HashMap<String, Student> studentMap;
    private HashMap<String, Teacher> teacherMap;
    private HashMap<String, List<String>> teacherStudentMap;

    public StudentRepository() {
        this.studentMap = new HashMap<>();
        this.teacherMap = new HashMap<>();
        this.teacherStudentMap = new HashMap<>();
    }


    public void addStudent(Student student) {
        studentMap.put(student.getName(), student);
    }

    public void addTeacher(Teacher teacher) {
        teacherMap.put(teacher.getName(), teacher);
    }

    public void addStudentTeacherPair(String student, String teacher) {
        if(studentMap.containsKey(student) && teacherMap.containsKey(teacher)) {
            List<String> currentStudents = new ArrayList<>();
            if(teacherMap.containsKey(teacher)) {
                currentStudents = teacherStudentMap.get(teacher);
            }

            currentStudents.add(student);
            teacherStudentMap.put(teacher, currentStudents);
        }
    }

    public Student getStudentByName(String studentName) {
        return studentMap.get(studentName);
    }

    public Teacher getTeacherByName(String teacherName) {
        return teacherMap.get(teacherName);
    }

    public List<String> getStudentsByTeacherName(String teacher) {
        List<String> studentsList = new ArrayList<>();
        if(teacherStudentMap.containsKey(teacher)) {
            studentsList = teacherStudentMap.get(teacher);
        }
        return studentsList;
    }

    public List<String> getAllStudents() {
        return new ArrayList<>(studentMap.keySet());
    }

    public void deleteTeacherByName(String teacher) {
        // we have to delete the teacher from both maps
        // teacherMap and teacherStudentMap and also
        // and also his students from studentMap

        List<String> students = new ArrayList<>();

        if(teacherStudentMap.containsKey(teacher)) {
            students = teacherStudentMap.get(teacher);
            // loop over list of students in teacherStudentMap
            for(String student : students) {
                if(studentMap.containsKey(student)) {
                    studentMap.remove(student);
                }
            }
            teacherStudentMap.remove(teacher);
        }
        if(teacherMap.containsKey(teacher)) {
            teacherMap.remove(teacher);
        }
    }

    public void deleteAllTeachers() {
        HashSet<String> studentSet = new HashSet<>();

        for(String teacher : teacherStudentMap.keySet()) {
            for(String student : teacherStudentMap.get(teacher)) {
                studentSet.add(student);
            }
        }

        for(String student : studentSet) {
            if(studentMap.containsKey(student)) {
                studentMap.remove(student);
            }
        }
    }
}
