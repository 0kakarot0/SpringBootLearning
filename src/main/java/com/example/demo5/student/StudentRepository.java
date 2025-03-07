package com.example.demo5.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository
        <Student, Long> {

    Optional<Student> findStudentByStudentEmail(String studentEmail);
}
