package com.example.demo1.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }


    public void addStudent(Student student) {
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());

        if (studentByEmail.isPresent()) {
            throw new IllegalStateException("Student with email " + student.getEmail() + " already exists");
        }

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean studentExists = studentRepository.existsById(studentId);
        if (!studentExists) {
            throw new IllegalStateException("Student does not exists in records");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, Student student) {
        boolean studentExist = studentRepository.existsById(studentId);
        if (studentExist &&
                student.getName() != null && !student.getName().isEmpty() &&
                student.getEmail() != null && !student.getEmail().isEmpty()
        ) {
            student.setId(studentId);
            studentRepository.save(student);
        }
    }
}
