package com.emysilva.demo.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class LoginResponse {
    private final String jwt;
}