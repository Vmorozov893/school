package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        logger.info("Was invoked method for get student by id");
        return studentRepository.findById(id).orElseThrow(()->{
            logger.error("No student found with id: {}",id);
            return new IllegalArgumentException("No student found with id:"+id);
        });
    }

    public Student editStudent(Long id, Student student) {
        logger.info("Was invoked method for edit student");
        Student student1 = studentRepository.findById(id).orElseThrow(()->{
            logger.error("No student found with id: {}",id);
            return new IllegalArgumentException("No student found with id:"+id);
        });
        student1.setName(student.getName());
        student1.setAge(student.getAge());
        return studentRepository.save(student1);
    }

    public void deleteStudent(Long id) {
        logger.info("Was invoked method for delete student");
        studentRepository.deleteById(id);
    }

    public List<Student> filterForAge(int age) {
        logger.info("Was invoked method for filter student by age");
        return studentRepository.findAll().stream().filter(e -> e.getAge() == age).collect(Collectors.toList());

    }
    public List<Student> findByAgeBetween(int min,int max){
        logger.info("Was invoked method for find student by age between min and max");
        return studentRepository.findByAgeBetween(min,max);
    }

    public Faculty facultyByStudent(Long id){
        logger.info("Was invoked method for get faculty by student id");
        return studentRepository.findById(id).orElseThrow(()->{
            logger.error("No student found with id: {}",id);
            return new IllegalArgumentException("No student found with id:"+id);
        }).getFaculty();
    }

    public int getStudentsCount(){
        logger.info("Was invoked method for get students count");
        return studentRepository.getStudentsCount();
    }

    public int getAverageAgeStudents(){
        logger.info("Was invoked method for get average age students");
        return studentRepository.getAverageAgeStudents();
    }

    public List<Student> getLastFiveStudents(){
        logger.info("Was invoked method for get last five students");
        return studentRepository.getLastFiveStudents();
    }
}
