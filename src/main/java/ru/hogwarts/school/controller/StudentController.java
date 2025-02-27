package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student){
        return studentService.createStudent(student);
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> editFaculty(@RequestBody Student student,@PathVariable Long id){
        Student editedStudent = studentService.editStudent(id,student);
        if(editedStudent == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editedStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteFaculty(@PathVariable Long id){
        Student deletedStudent = studentService.deleteStudent(id);
        if(deletedStudent == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deletedStudent);
    }

    @GetMapping
    public ResponseEntity<List<Student>> StudentsByAge(@RequestParam(required = false) int age){
        List<Student> students = studentService.filterForAge(age);
        if(students == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

}
