package com.example.pbl7_backend.controller;

import com.example.pbl7_backend.dto.authentication.LoginRequest;
import com.example.pbl7_backend.dto.authentication.LoginResponse;
import com.example.pbl7_backend.dto.authentication.RegisterRequest;
import com.example.pbl7_backend.dto.authentication.RegisterResponse;
import com.example.pbl7_backend.dto.user.UserDTO;
import com.example.pbl7_backend.model.UserEntity;
import com.example.pbl7_backend.service.AuthenticationService;
import com.example.pbl7_backend.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authService;
    private final JwtService jwtService;
    private final ModelMapper mapper;


    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest dto) {
        UserEntity user = authService.register(dto);
        RegisterResponse resBody = mapper.map(user, RegisterResponse.class);

        return ResponseEntity.ok(resBody);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest dto) {
        UserEntity user = authService.authenticate(dto);
        String jwt = jwtService.generateToken(user);

        return ResponseEntity.ok(LoginResponse.builder()
                .accessToken(jwt)
                .userDTO(mapper.map(user, UserDTO.class))
                .expiresAt(jwtService.extractExpiration(jwt))
                .build());
    }
}