package com.example.demo2.student.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationSuccessResponse {
    private String studentId;
    private String message;
    private LocalDateTime timestamp;
}
