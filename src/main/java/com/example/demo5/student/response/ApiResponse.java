package com.example.demo5.student.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"timeStamp", "message", "data"})
public class ApiResponse<T> {
    private LocalDateTime timeStamp;
    private String message;
    private T data;
}
