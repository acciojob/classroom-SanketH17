package com.driver;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
@Repository
public class StudentRepository {
    private HashMap<String,Student> studentDB;
    private HashMap<String,Teacher> teacherDB;
    private HashMap<String,List<Student>> studentTeacherDB;
    StudentRepository(){
        this.studentDB=new HashMap<String,Student>();
        this.teacherDB=new HashMap<String,Teacher>();
        this.studentTeacherDB=new HashMap<String,List<Student>>();
    }
    public void addStudent(Student student) {
        studentDB.put(student.getName(),student);
    }


    public void addTeacher(Teacher teacher) {
        teacherDB.put(teacher.getName(),teacher);
    }

    public void addStudentTeacherPair(String student, String teacher) {
        List<Student> list=null;
        list.addAll(studentTeacherDB.get(teacher));
        list.add(studentDB.remove(student));
        studentTeacherDB.put(teacher,list);
    }

    public Student getStudentByName(String name) {
        Student student=null;
        if(studentDB.containsKey(name)){
            return studentDB.get(name);
        }
        for(List<Student> list:studentTeacherDB.values()){
            for(Student s:list){
                if(s.getName()==name){
                    return s;
                }
            }
        }
        return student;
    }

    public Teacher getTeacherByName(String name) {
        return teacherDB.get(name);
    }

    public List<String> getStudentsByTeacherName(String teacher) {
        List<String> students=null;
        List<Student> list=studentTeacherDB.get(teacher);
        for(Student s:list){
            students.add(s.getName());
        }
        return students;
    }

    public List<String> getAllStudents() {
        List<String> students=null;
        for(String s:studentDB.keySet()){
            students.add(s);
        }
        for(List<Student> list:studentTeacherDB.values()){
            for(Student s:list){
                students.add(s.getName());
            }
        }
        return students;
    }

    public void deleteTeacherByName(String teacher) {
        studentTeacherDB.remove(teacher);
    }

    public void deleteAllTeachers() {
        studentTeacherDB.clear();
        teacherDB.clear();
    }
}