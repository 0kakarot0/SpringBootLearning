package com.example.demo5.student;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {

            List<Student> listOfStudents = new ArrayList<>();
            Random randomNumber = new Random();
            int maxLimit = randomNumber.nextInt(3, 5);
            for (int i = 0; i < maxLimit; i++) {
                Result result = getStudentData();
                listOfStudents.add(
                        new Student(result.studentName(), result.studentEmail(), result.dob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                );
            }
            studentRepository.saveAll(listOfStudents);
        };
    }

    private static Result getStudentData() {
        String studentName = Faker.instance().name().fullName();
        String studentEmail = studentEmail(studentName);
        Date dob = Faker.instance().date().birthday();

        return new Result(studentName, studentEmail, dob);
    }

    private record Result(String studentName, String studentEmail, Date dob) {
    }

    private static String studentEmail(String studentName) {
        return studentName.toLowerCase().replace(" ", "") + "@gmail.com";
    }

}
