package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty){
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Long id, Faculty faculty) {
        Faculty faculty1 = facultyRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        faculty1.setName(faculty.getName());
        faculty1.setColor(faculty.getColor());
        return facultyRepository.save(faculty1);
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> filterForColor(String color){
        return facultyRepository.findAll().stream().filter(e->e.getColor().equals(color)).collect(Collectors.toList());
    }

}
