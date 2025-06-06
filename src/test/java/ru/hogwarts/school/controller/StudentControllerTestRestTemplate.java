package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestRestTemplate {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    StudentRepository repository;

    @BeforeEach
    public void clearDatabase() {
        repository.deleteAll();
    }

    @Test
    void shouldCreateStudent() {
        Student student = new Student("Garry Potter", 11);

        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                student,
                Student.class
        );

        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Student actualStudent = studentResponseEntity.getBody();
        assertNotNull(actualStudent.getId());
        assertEquals(actualStudent.getName(), student.getName());
        assertEquals(actualStudent.getAge(), student.getAge());

    }

    @Test
    void shouldGetStudentInfo() {
        Student student = new Student("Garry Potter", 11);
        student = repository.save(student);

        ResponseEntity<Student> studentResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + student.getId(),
                Student.class
        );

        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Student actualStudent = studentResponseEntity.getBody();
        assertEquals(actualStudent.getId(), student.getId());
        assertEquals(actualStudent.getName(), student.getName());
        assertEquals(actualStudent.getAge(), student.getAge());
    }

    @Test
    void shouldEditStudent() {
        Student student = new Student("Garry Potter", 11);
        student = repository.save(student);

        Student newStudent = new Student("Ron Weasley", 13);

        HttpEntity<Student> entity = new HttpEntity<>(newStudent);
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student/" + student.getId(),
                HttpMethod.PUT,
                entity,
                Student.class
        );

        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        Student actualStudent = studentResponseEntity.getBody();
        assertEquals(actualStudent.getId(), student.getId());
        assertEquals(actualStudent.getName(), newStudent.getName());
        assertEquals(actualStudent.getAge(), newStudent.getAge());

    }

    @Test
    void shouldDeleteStudent() {
        Student student = new Student("Garry Potter", 11);
        student = repository.save(student);

        ResponseEntity<Void> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student/" + student.getId(),
                HttpMethod.DELETE,
                null,
                Void.class
        );
        assertEquals(HttpStatus.NO_CONTENT, studentResponseEntity.getStatusCode());
    }

    @Test
    void shouldStudentsByAge(){
        int age=12;
        Student student1 = new Student("Garry Potter", age-1);
        Student student2 = new Student("Ron Weasley", age);
        Student student3 = new Student("Hermione Granger", age);
        student1 = repository.save(student1);
        student2 = repository.save(student2);
        student3 = repository.save(student3);
        final Long id1 = student1.getId();
        final Long id2 = student2.getId();
        final Long id3 = student3.getId();


        ResponseEntity<List<Student>> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student?age=" + age,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>(){}
        );

        assertNotNull(studentResponseEntity);
        assertEquals(studentResponseEntity.getStatusCode(), HttpStatusCode.valueOf(200));

        List<Student> actualStudents = studentResponseEntity.getBody();
        assertEquals(2, actualStudents.size());
        Student actualStudent1 = actualStudents.stream().filter(e->e.getId().equals(id1)).findFirst().orElse(null);
        Student actualStudent2 = actualStudents.stream().filter(e->e.getId().equals(id2)).findFirst().orElse(null);
        Student actualStudent3 = actualStudents.stream().filter(e->e.getId().equals(id3)).findFirst().orElse(null);

        assertNull(actualStudent1);
        assertNotNull(actualStudent2);
        assertNotNull(actualStudent3);
        assertEquals(actualStudent2.getId(), student2.getId());
        assertEquals(actualStudent2.getName(), student2.getName());
        assertEquals(actualStudent2.getAge(), student2.getAge());
        assertEquals(actualStudent3.getId(), student3.getId());
        assertEquals(actualStudent3.getName(), student3.getName());
        assertEquals(actualStudent3.getAge(), student3.getAge());


    }

    @Test
    void shouldFindByAgeBetween() {

    }
    @Test
    void shouldFacultyByStudent(){

    }

}
