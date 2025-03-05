package com.example.demo3.student;

import com.example.demo3.student.apiResponse.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> fetchAllStudentsFromDatabase() {
        return studentRepository.findAll();
    }

    public Object addNewStudentToDatabase(Student student) {
        Optional<Student> studentExists = studentRepository.findStudentByEmail(student.getEmail());
        if (studentExists.isPresent()) {
            return new ApiResponse<Student>(HttpStatus.CONFLICT, "Student with email " + student.getEmail() + " already exist", studentExists.get());
        } else {
            studentRepository.save(student);
            return new ApiResponse<Student>(HttpStatus.CREATED, "Student registered successfully", student);
        }
    }

    public Object updateStudentDataInDatabase(Long studentId, Student student) {
        boolean studentExists = studentRepository.existsById(studentId);

        if (!studentExists) {
            return new ApiResponse<Student>(HttpStatus.NOT_FOUND, "Student will student id + " + studentId + " does not exists", student);
        } else {
            student.setId(studentId);
            studentRepository.save(student);
            return new ApiResponse<Student>(HttpStatus.OK, "Student data with  student id: " + studentId + " updated successfully", student);
        }
    }

    public Object patchExistingStudentData(Long studentId, Map<String, Object> studentObject) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (!student.isPresent()) {
            return new ApiResponse<Student>(HttpStatus.NOT_FOUND, "Student will id + " + studentId + " does not exists", null);
        } else {
            Student student1 = student.orElse(null);
            student1.setId(studentId);

            studentObject.forEach((key, value) -> {
                switch (key) {
                    case "name":
                        student1.setName((String) value);
                        break;
                    case "email":
                        student1.setEmail((String) value);
                        break;
                    case "dob":
                        student1.setDob(LocalDate.parse((String) value));
                        break;

                    default:
                        throw new IllegalStateException("Invalid field: " + key);
                }
            });
            studentRepository.save(student1);
            return new ApiResponse<Student>(HttpStatus.OK, "Student data with  student id: " + studentId + " updated successfully", student1);
        }
    }

    public Object deleteStudentFromDatabase(Long studentId) {
        boolean studentExists = studentRepository.existsById(studentId);
        if (!studentExists) {
            return new ApiResponse<Student>(HttpStatus.NOT_FOUND, "Student will student id + " + studentId + " does not exists", null);
        } else {
            studentRepository.deleteById(studentId);
            return new ApiResponse<Student>(HttpStatus.OK, "Student data with  student id: " + studentId + " deleted successfully", null);
        }
    }
}
