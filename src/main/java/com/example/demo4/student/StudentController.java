package com.example.demo4.student;

import com.example.demo4.student.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("all")
    public ApiResponse<List<Student>> getAllStudent() {
        return studentService.getAllStudentFromDatabase();
    }

    @PostMapping("/register")
    public ApiResponse<Student> registerStudent(@RequestBody Student student) {
        return studentService.addStudentToDataBase(student);
    }

    @PutMapping(path = "updateStudent/{studentId}")
    public ApiResponse<Student> updateStudentRecord(
            @PathVariable("studentId") Long studentId,
            @RequestBody Student student) {
        return studentService.updateStudentInDataBase(studentId, student);
    }

    @PatchMapping(path = "patchStudent/{studentId}")
    public ApiResponse<Student> patchStudentRecord(
            @PathVariable("studentId") Long studentId,
            @RequestBody Map<String, Object> studentObject) {
        return studentService.patchStudentInDataBase(studentId, studentObject);
    }

    @DeleteMapping(path = "deleteStudent/{studentId}")
    public ApiResponse<Student> deleteStudent(
            @PathVariable("studentId") Long studentId
    ) {
        return studentService.deleteStudentInDataBase(studentId);
    }
}
