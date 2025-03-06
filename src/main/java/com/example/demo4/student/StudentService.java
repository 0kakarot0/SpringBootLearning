package com.example.demo4.student;

import com.example.demo4.student.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    private static final String studentWithEmailString = "Student with email ";
    private static final String studentWithIDString = "Student with ID ";

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public ApiResponse<List<Student>> getAllStudentFromDatabase() {
        List<Student> studentList = studentRepository.findAll();
        int studentListSize = studentList.size();
        if (!studentList.isEmpty()) {

            return new ApiResponse<>(
                    HttpStatus.OK,
                    studentListSize + " students retrieved successfully",
                    studentList,
                    LocalDateTime.now()
            );
        } else {
            return new ApiResponse<>(
                    HttpStatus.NO_CONTENT,
                    studentListSize + " students retrieved successfully",
                    List.of(),
                    LocalDateTime.now()
            );
        }
    }

    public ApiResponse<Student> addStudentToDataBase(Student student) {
        Optional<Student> isStudentEmailExist = studentRepository.findStudentByEmail(student.getStudentEmail());

        if (isStudentEmailExist.isPresent()) {
            return new ApiResponse<>(
                    HttpStatus.CONFLICT,
                    studentWithEmailString + student.getStudentEmail() + " already exists",
                    student,
                    LocalDateTime.now()
            );
        } else {
            studentRepository.save(student);
            return new ApiResponse<>(
                    HttpStatus.CREATED,
                    studentWithEmailString + student.getStudentEmail() + " register successfully!",
                    student,
                    LocalDateTime.now()
            );
        }

    }

    public ApiResponse<Student> updateStudentInDataBase(Long studentId, Student student) {
        Optional<Student> doesStudentExistInDataBase = studentRepository.findById(studentId);

        if (doesStudentExistInDataBase.isEmpty()) {
            return new ApiResponse<>(
                    HttpStatus.NO_CONTENT,
                    studentWithEmailString + student.getStudentEmail() + " does not exists",
                    student,
                    LocalDateTime.now()
            );
        } else {
            student.setId(doesStudentExistInDataBase.get().getId());
            studentRepository.save(student);
            return new ApiResponse<>(
                    HttpStatus.NO_CONTENT,
                    studentWithIDString + student.getId() + " updated successfully!",
                    student,
                    LocalDateTime.now()
            );
        }
    }

    public ApiResponse<Student> patchStudentInDataBase(Long studentId, Map<String, Object> student) {
        Optional<Student> doesStudentExistInDataBase = studentRepository.findById(studentId);

        if (doesStudentExistInDataBase.isPresent()) {
            Student student1 = doesStudentExistInDataBase.get();
            student.forEach((key, value) -> {
                switch (key) {
                    case "studentName":
                        student1.setStudentName((String) value);
                        break;
                    case "studentEmail":
                        student1.setStudentEmail((String) value);
                        break;
                    case "dob":
                        student1.setDob(LocalDate.parse((String) value));
                        break;
                    default:
                        throw new IllegalStateException("incorrect student data");
                }
            });

            studentRepository.save(student1);
            return new ApiResponse<>(
                    HttpStatus.OK,
                    "Student record updated successfully!",
                    student1,
                    LocalDateTime.now()
            );
        } else {
            return new ApiResponse<>(
                    HttpStatus.NO_CONTENT,
                    "Student record does not exists!",
                    null,
                    LocalDateTime.now()
            );
        }
    }

    public ApiResponse<Student> deleteStudentInDataBase(Long studentId) {
        boolean doesStudentExists = studentRepository.existsById(studentId);

        if (doesStudentExists) {
            Optional<Student> student = studentRepository.findById(studentId);
            Student student1 = student.orElse(null);

            studentRepository.deleteById(studentId);
            return new ApiResponse<>(
                    HttpStatus.OK,
                    studentWithIDString + studentId + " deleted successfully!",
                    student1,
                    LocalDateTime.now()
            );
        } else {
            return new ApiResponse<>(
                    HttpStatus.BAD_REQUEST,
                    studentWithIDString + studentId + "does not exists",
                    null,
                    LocalDateTime.now()
            );
        }
    }
}
