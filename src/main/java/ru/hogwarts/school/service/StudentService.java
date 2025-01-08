package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;

@Service
public class StudentService {

    private Long count = 0L;

    HashMap<Long, Student> studentsHashMap = new HashMap<>();

    public Student createStudent(Student student){
        studentsHashMap.put(++count,student);
        student.setId(count);
        return student;
    }

    public Student getStudentById(Long id) {
        return studentsHashMap.get(id);
    }

    public Student editStudent(Long id, Student student) {
        if(!studentsHashMap.containsKey(id)){
            return null;
        }
        student.setId(id);
        studentsHashMap.put(id, student);
        return student;
    }

    public Student deleteStudent(Long id) {
        return studentsHashMap.remove(id);
    }
}
