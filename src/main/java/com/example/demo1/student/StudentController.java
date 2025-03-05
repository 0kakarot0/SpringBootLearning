package com.example.demo1.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static java.time.Month.APRIL;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping
    public void registerStudent(@RequestBody Student student) {
        studentService.addStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId") Long studentId, @RequestBody Student student){
        studentService.updateStudent(studentId,student);
    }
}
