package com.example.demo4.student.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@JsonPropertyOrder({"timestamp","status","message", "data"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private HttpStatus status;

    private String message;
    private T data;
    private LocalDateTime timestamp;
}
