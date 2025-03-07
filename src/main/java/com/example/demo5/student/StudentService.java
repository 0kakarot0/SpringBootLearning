package com.example.demo5.student;

import com.example.demo5.student.dto.StudentEmailDTO;
import com.example.demo5.student.dto.StudentPatchDTO;
import com.example.demo5.student.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    public static final String STUDENT_RECORD_NOT_FOUND_WITH_EMAIL = "Student record not found with email ";
    public static final String STUDENT_NOT_FOUND_ID = "Student record not found with id: ";
    public static final String DOES_NOT_EXIST = " does not exist";
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents() {
        List<Student> listOfStudents = studentRepository.findAll();
        ApiResponse<List<Student>> apiResponse;
        if (listOfStudents.isEmpty()) {
            apiResponse = new ApiResponse<>(LocalDateTime.now(), "No Students Record Found", listOfStudents);
        } else {
            apiResponse = new ApiResponse<>(LocalDateTime.now(), listOfStudents.size() + " fetched successFully", listOfStudents);
        }
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    public ResponseEntity<ApiResponse<Student>> getStudentRecordByEmailFromDatabase(StudentEmailDTO studentEmail) {
        String currentStudentEmail = studentEmail.getStudentEmail();
        Optional<Student> getStudentByEmail = studentRepository.findStudentByStudentEmail(currentStudentEmail);
        ApiResponse<Student> apiResponse;
        apiResponse = getStudentByEmail.map(student -> new ApiResponse<>(LocalDateTime.now(), "Student record fetched successfully!", student)).orElseGet(() -> new ApiResponse<>(LocalDateTime.now(), STUDENT_RECORD_NOT_FOUND_WITH_EMAIL + currentStudentEmail, null));
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    public ResponseEntity<ApiResponse<Student>> addNewStudentInDatabase(Student student) {
        Optional<Student> isStudentExistWithEmail = getStudent(student);

        if (isStudentExistWithEmail.isPresent()) {

            ApiResponse<Student> apiResponse = new ApiResponse<>(LocalDateTime.now(), STUDENT_RECORD_NOT_FOUND_WITH_EMAIL + student.getStudentEmail() + " already exist", student);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } else {
            studentRepository.save(student);
            ApiResponse<Student> apiResponse = new ApiResponse<>(LocalDateTime.now(), "Student record added successfully", student);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

        }
    }


    public ResponseEntity<ApiResponse<Student>> updateStudentRecordInDataBase(Long studentId, StudentPatchDTO student) {
        Optional<Student> isStudentExistWithEmail = studentRepository.findById(studentId);

        if (isStudentExistWithEmail.isPresent()) {
            Student student1 = new Student();
            student1.setId(isStudentExistWithEmail.orElseThrow().getId());
            student1.setStudentName(student.getStudentName());
            student1.setStudentEmail(student.getStudentEmail());
            student1.setDob(student.getDob());
            studentRepository.save(student1);
            ApiResponse<Student> apiResponse = new ApiResponse<>(LocalDateTime.now(), "Student record updated successfully", student1);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } else {
            ApiResponse<Student> apiResponse = new ApiResponse<>(LocalDateTime.now(), STUDENT_RECORD_NOT_FOUND_WITH_EMAIL + student.getStudentEmail() + DOES_NOT_EXIST, null);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
    }

    public ResponseEntity<ApiResponse<Student>> patchStudentRecordInDatabase(Long studentId, StudentPatchDTO studentPatchDTO) {
        Optional<Student> isStudentExistWithID = studentRepository.findById(studentId);
        if (isStudentExistWithID.isPresent()) {
            Student student = getStudent(studentPatchDTO, isStudentExistWithID.get());

            studentRepository.save(student);
            ApiResponse<Student> apiResponse = new ApiResponse<>(LocalDateTime.now(), "Student record updated successfully", student);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } else {
            ApiResponse<Student> apiResponse = new ApiResponse<>(LocalDateTime.now(), STUDENT_NOT_FOUND_ID + studentId + DOES_NOT_EXIST, null);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
    }

    private static Student getStudent(StudentPatchDTO studentPatchDTO, Student student) {

        // Update only non-null fields from the DTO
        if (studentPatchDTO.getStudentName() != null) {
            student.setStudentName(studentPatchDTO.getStudentName());
        }
        if (studentPatchDTO.getStudentEmail() != null) {
            student.setStudentEmail(studentPatchDTO.getStudentEmail());
        }
        if (studentPatchDTO.getDob() != null) {
            student.setDob(studentPatchDTO.getDob());
        }
        return student;
    }

    public ResponseEntity<ApiResponse<Student>> deleteStudentRecordFromDatabase(Long studentId) {
        boolean isStudentExist = studentRepository.existsById(studentId);
        if (isStudentExist) {
            Student student = studentRepository.findById(studentId).orElseThrow();

            studentRepository.deleteById(studentId);
            ApiResponse<Student> apiResponse = new ApiResponse<>(LocalDateTime.now(), "Student record deleted successfully", student);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        } else {
            ApiResponse<Student> apiResponse = new ApiResponse<>(LocalDateTime.now(), STUDENT_NOT_FOUND_ID + studentId + DOES_NOT_EXIST, null);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
    }


    private Optional<Student> getStudent(Student student) {
        return studentRepository.findStudentByStudentEmail(student.getStudentEmail());
    }

}
