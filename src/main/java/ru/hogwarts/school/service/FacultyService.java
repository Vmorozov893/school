package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private Long count = 0L;

    private final HashMap<Long, Faculty> facultyHashMap = new HashMap<>();



    public Faculty createFaculty(Faculty faculty){
        facultyHashMap.put(++count,faculty);
        faculty.setId(count);
        return faculty;
    }

    public Faculty getFacultyById(Long id) {
        return facultyHashMap.get(id);
    }

    public Faculty editFaculty(Long id, Faculty faculty) {
        if(!facultyHashMap.containsKey(id)){
            return null;
        }
        faculty.setId(id);
        facultyHashMap.put(id, faculty);
        return faculty;
    }

    public Faculty deleteFaculty(Long id) {
        return facultyHashMap.remove(id);
    }

    public List<Faculty> filterForColor(String color){
        return facultyHashMap.values().stream().filter(e->e.getColor().equals(color)).collect(Collectors.toList());
    }

}
