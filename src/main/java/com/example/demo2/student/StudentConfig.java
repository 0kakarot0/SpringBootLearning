package com.example.demo2.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.time.Month.DECEMBER;
import static java.time.Month.MARCH;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return arg -> {
            Student maria = new Student(
                    "Maria",
                    "maria@gmail.com",
                    LocalDate.of(1991, MARCH, 1)
            );
            Student alex = new Student(
                    "Alex",
                    "alex@gmail.com",
                    LocalDate.of(1997, DECEMBER, 23)
            );
            studentRepository.saveAll(List.of(maria, alex));
        };
    }
}


