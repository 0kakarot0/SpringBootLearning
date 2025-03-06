package com.example.demo4.student;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
public class StudentConfig {
    private Faker faker;

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository){
        faker = new Faker();

        List<Student> studentList = new ArrayList<>();

        return args -> {
            for (int i = 0; i < 3; i++) {
                String name = faker.name().fullName();
                String email = name.toLowerCase().replace(" ","") + "@gmail.com";
                Date dob = faker.date().birthday();
                Student student = new Student(
                        name,
                        email,
                        dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                );
                studentList.add(student);
            }
            studentRepository.saveAll(studentList);
        };
    }
}
