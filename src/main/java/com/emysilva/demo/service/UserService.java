package com.emysilva.demo.service;

import com.emysilva.demo.dto.SignupRequest;

public interface UserService {
    void registerUser(SignupRequest signUpRequest);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
