package com.example.demo1.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.APRIL;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student monaLisa = new Student(
                    "Mona Lisa",
                    "lisamona@gmail.com",
                    LocalDate.of(2001, APRIL, 2)
            );
            Student david = new Student(
                    "David",
                    "david@gmail.com",
                    LocalDate.of(2000, APRIL, 2)
            );

            repository.saveAll(
                    List.of(
                            monaLisa,
                            david
                    )
            );
        };
    }
}
