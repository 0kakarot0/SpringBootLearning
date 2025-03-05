package com.example.demo3.student;

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

    @GetMapping("/all")
    public List<Student> getAllStudent() {
        return studentService.fetchAllStudentsFromDatabase();
    }


    @PostMapping("/register")
    public Object registerNewStudent(@RequestBody Student student) {
        return studentService.addNewStudentToDatabase(student);
    }

    @PutMapping(path = "/updateStudent/{studentId}")
    public Object updateStudentData(@PathVariable("studentId") Long studentId, @RequestBody Student student) {
        return studentService.updateStudentDataInDatabase(studentId, student);
    }

    @PatchMapping(path = "/patchStudent/{studentId}")
    public Object patchExistingStudent(@PathVariable("studentId") Long studentId, @RequestBody Map<String, Object> studentObject){
        return studentService.patchExistingStudentData(studentId, studentObject);
    }

    @DeleteMapping(path = "{studentId}")
    public Object deleteStudent(@PathVariable("studentId") Long studentId){
            return studentService.deleteStudentFromDatabase(studentId);
    }
}
