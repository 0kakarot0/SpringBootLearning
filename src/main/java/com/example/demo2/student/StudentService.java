package com.example.demo2.student;

import com.example.demo2.student.response.RegistrationSuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
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

    public List<Student> fetchAllTheStudents() {
        return studentRepository.findAll();
    }

    public RegistrationSuccessResponse registerStudent(Student student) {
        Optional<Student> doesStudentExists = studentRepository.findStudentByEmail(student.getEmail());

        if (doesStudentExists.isPresent())
            throw new IllegalStateException("Student with email " + student.getEmail() + " already exists");
        else {
            studentRepository.save(student);
            Map<Object, String> message = new HashMap<>();
            message.put("message", "Student registered successfully");
            message.put("timeStamp", LocalDate.now().toString());
            message.put("studentId", student.getId().toString());
            return new RegistrationSuccessResponse(student.getId().toString(), "Student registered successfully", LocalDateTime.now());
        }
    }


    public String deleteStudent(Long studentId) {
        boolean isStudentExist = studentRepository.existsById(studentId);

        if (!isStudentExist) {
            throw new IllegalStateException("Student with ID " + studentId + " does not exists");
        } else {
            studentRepository.deleteById(studentId);
            return "Student Record Deleted Successfully";
        }
    }

    public String updateStudentData(Long studentId, Student student) {
        boolean isStudentExist = studentRepository.existsById(studentId);

        if (!isStudentExist) {
            throw new IllegalStateException("Student with ID " + studentId + " does not exists");
        } else {
            student.setId(studentId);
            studentRepository.save(student);
            return "record has been updated successfully ";
        }
    }
}
