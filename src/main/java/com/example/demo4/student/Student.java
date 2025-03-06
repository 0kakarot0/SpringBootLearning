package com.example.demo4.student;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;


@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )

    private Long id;

    @Nonnull
    private String studentName;

    @Nonnull
    private String studentEmail;

    @Nonnull
    private LocalDate dob;

    @Transient
    private int age;

    public Student(@Nonnull String studentName, @Nonnull String studentEmail, @Nonnull LocalDate localDate) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.dob = localDate;
    }

    public int getAge() {
        return Period.between(dob, LocalDate.now()).getYears();
    }
}
