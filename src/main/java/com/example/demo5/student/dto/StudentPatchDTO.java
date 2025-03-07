package com.example.demo5.student.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentPatchDTO {
    private String studentName;
    private String studentEmail;
    private LocalDate dob;
}
