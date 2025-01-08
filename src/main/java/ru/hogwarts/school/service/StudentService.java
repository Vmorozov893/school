package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;

public class StudentService {

    private Long count = 0L;

    HashMap<Long, Student> studentsHashMap = new HashMap<>();

    public Student createStudent(Student student){
        studentsHashMap.put(++count,student);
        return student;
    }

    public Student getStudentById(Long id) {
        return studentsHashMap.get(id);
    }

    public Student editStudent(Long id, Student student) {
        studentsHashMap.put(id, student);
        return student;
    }

    public Student deleteStudent(Long id) {
        return studentsHashMap.remove(id);
    }
}
