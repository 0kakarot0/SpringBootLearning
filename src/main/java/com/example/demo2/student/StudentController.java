package com.example.demo2.student;

import com.example.demo2.student.response.RegistrationSuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all")
    public List<Student> getAllStudent() {
        return studentService.fetchAllTheStudents();
    }

    @PostMapping("/register")
    public RegistrationSuccessResponse registerNewStudent(@RequestBody Student student) {
        return studentService.registerStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public String deleteStudentById(@PathVariable("studentId") Long studentId) {
        return studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    public String updateStudentRecord(
            @PathVariable("studentId") Long studentId,
            @RequestBody Student student
    ) {
        return studentService.updateStudentData(studentId, student);
    }

}
