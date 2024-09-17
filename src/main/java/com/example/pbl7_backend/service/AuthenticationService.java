package com.example.pbl7_backend.service;

import com.example.pbl7_backend.dto.authentication.LoginRequest;
import com.example.pbl7_backend.dto.authentication.RegisterRequest;
import com.example.pbl7_backend.model.UserEntity;

public interface AuthenticationService {
    UserEntity authenticate(LoginRequest dto);

    UserEntity register(RegisterRequest dto);
}
