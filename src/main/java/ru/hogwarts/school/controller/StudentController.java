package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
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
    public ResponseEntity<Student> editStudent(@RequestBody Student student,@PathVariable Long id){
        Student editedStudent = studentService.editStudent(id,student);
        if(editedStudent == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(editedStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Student>> StudentsByAge(@RequestParam(required = false) int age){
        List<Student> students = studentService.filterForAge(age);
        if(students == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("findByAgeBetween")
    public ResponseEntity<List<Student>> findByAgeBetween(int min, int max) {
        List<Student> students = studentService.findByAgeBetween(min, max);
        if (students == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(students);
    }
    @GetMapping("facultyByStudent")
    public Faculty facultyByStudent(Long id){
        return studentService.facultyByStudent(id);
    }

    @GetMapping("getStudentsCount")
    public int getStudentsCount(){
        return studentService.getStudentsCount();
    }

    @GetMapping("getAverageAgeStudents")
    public int getAverageAgeStudents(){
        return studentService.getAverageAgeStudents();
    }

    @GetMapping("getLastFiveStudents")
    public List<Student> getLastFiveStudents(){
        return studentService.getLastFiveStudents();
    }

}
