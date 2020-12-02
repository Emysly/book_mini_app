package com.emysilva.demo.config.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrors {
    private String message;
    private String details;
    HttpStatus status;
    LocalDateTime timestamp;
}
