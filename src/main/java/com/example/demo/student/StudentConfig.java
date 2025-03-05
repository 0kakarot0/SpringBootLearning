package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return arg -> {
            Student julia = new Student(
                    "Julia",
                    "julia@gmail.com",
                    LocalDate.of(1991, 12, 11)
            );
            Student angela = new Student(
                    "Angela",
                    "angela@gmail.com",
                    LocalDate.of(1995, 12, 11)
            );

            repository.saveAll(
                    List.of(julia, angela)
            );
        };
    }
}
