package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyById(Long id) {
        logger.info("Was invoked method for get faculty by id");
        Optional<Faculty> faculty = facultyRepository.findById(id);

        if (faculty.isEmpty()) {
            logger.warn("No faculty found with id: {}", id);
        } else {
            logger.debug("Faculty found: {}", faculty.get());
        }

        return faculty.get();
    }

    public Faculty editFaculty(Long id, Faculty faculty) {
        logger.info("Was invoked method for edit faculty");
        Faculty faculty1 = facultyRepository.findById(id).orElseThrow(() -> {
            logger.error("No faculty found with id: {}", id);
            return new IllegalArgumentException("No faculty found with id: " + id);
        });
        faculty1.setName(faculty.getName());
        faculty1.setColor(faculty.getColor());
        return facultyRepository.save(faculty1);
    }

    public void deleteFaculty(Long id) {
        logger.info("Was invoked method for delete faculty");
        facultyRepository.deleteById(id);
    }

    public List<Faculty> filterForColor(String color) {
        logger.info("Was invoked method for filter faculty by color");
        return facultyRepository.findAll().stream().filter(e -> e.getColor().equals(color)).collect(Collectors.toList());
    }

    public List<Faculty> findByNameOrColorIgnoreCase(String name, String color) {
        logger.info("Was invoked method for find by name or color ignore case");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    public List<Student> studentsByFaculty(Long id) {
        logger.info("Was invoked method for get students by faculty id");
        return facultyRepository.findById(id).orElseThrow(() -> {
            logger.error("No faculty found with id: {}", id);
            return new IllegalArgumentException("No faculty found with id: " + id);
        }).getStudents();
    }


}
