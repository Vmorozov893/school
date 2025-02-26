package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
        Faculty faculty = facultyService.getFacultyById(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty){
        return facultyService.createFaculty(faculty);
    }

    @PutMapping("{id}")
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty,@PathVariable Long id){
        Faculty editedFaculty = facultyService.editFaculty(id,faculty);
        if(editedFaculty == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editedFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteFaculty(@PathVariable Long id){
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok("OK");
    }

    @GetMapping
    public ResponseEntity<List<Faculty>> FacultyByColor(@RequestParam(required = false) String color){
        List<Faculty> faculties = facultyService.filterForColor(color);
        if(faculties == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("findByNameIgnoreCaseOrColorIgnoreCase")
    public ResponseEntity<List<Faculty>> findByNameIgnoreCaseOrColorIgnoreCase(String string){
        List<Faculty> faculties = facultyService.findByNameIgnoreCaseOrColorIgnoreCase(string,string);
        if(faculties == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("studentsByFaculty")
    public List<Student> studentsByFaculty(Long id){
        return facultyService.studentsByFaculty(id);
    }

}
