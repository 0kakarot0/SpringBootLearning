package com.example.demo3.student;

import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Configuration
public class StudentConfig {
    Faker dataFaker = new Faker();

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            List<Student> studentList = new ArrayList<>();;
            for (int i = 0; i < 2; i++) {
                String name = String.valueOf(dataFaker.name().name());
                Random random = new Random();
                int year = random.nextInt(1990, 2000);
                int month = random.nextInt(1, 12);
                int day = random.nextInt(1, 30);

                // Student
                Student student = new Student(
                        name,
                        name.toLowerCase().replace(" ","") + "@gmail.com",
                        LocalDate.of(year, month, day)
                );
                studentList.add(student);
            }
            studentRepository.saveAll(studentList);
        };
    }
}
