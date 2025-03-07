package com.example.demo5.student;

import com.example.demo5.student.dto.StudentEmailDTO;
import com.example.demo5.student.dto.StudentPatchDTO;
import com.example.demo5.student.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudent() {
        return studentService.getAllStudents();
    }

    @GetMapping("/getStudentByEmail")
    public ResponseEntity<ApiResponse<Student>> getStudentRecordByEmail(@RequestBody StudentEmailDTO studentEmail) {
        return studentService.getStudentRecordByEmailFromDatabase(studentEmail);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Student>> registerNewStudent(@RequestBody Student student) {
        return studentService.addNewStudentInDatabase(student);
    }

    @PutMapping(path = "updateStudent/{studentId}")
    public ResponseEntity<ApiResponse<Student>> updateStudentRecord(@PathVariable("studentId") Long studentId, @RequestBody StudentPatchDTO student) {
        return studentService.updateStudentRecordInDataBase(studentId, student);
    }

    @PatchMapping(path = "patchStudent/{studentId}")
    public ResponseEntity<ApiResponse<Student>> patchStudentRecord(
            @PathVariable("studentId") Long studentId, @RequestBody
            StudentPatchDTO studentPatchDTO) {
        return studentService.patchStudentRecordInDatabase(studentId, studentPatchDTO);
    }

    @DeleteMapping(path = "deleteStudent/{studentId}")
    public ResponseEntity<ApiResponse<Student>> deleteStudentRecord(@PathVariable("studentId") Long studentId) {
        return studentService.deleteStudentRecordFromDatabase(studentId);
    }

}
