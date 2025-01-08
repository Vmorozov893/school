package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;

public class FacultyService {

    private Long count = 0L;

    HashMap<Long, Faculty> facultyHashMap = new HashMap<>();

    public Faculty createFaculty(Faculty faculty){
        facultyHashMap.put(++count,faculty);
        return faculty;
    }

    public Faculty getFacultyById(Long id) {
        return facultyHashMap.get(id);
    }

    public Faculty editFaculty(Long id, Faculty faculty) {
        facultyHashMap.put(id, faculty);
        return faculty;
    }

    public Faculty deleteFaculty(Long id) {
        return facultyHashMap.remove(id);
    }

}
