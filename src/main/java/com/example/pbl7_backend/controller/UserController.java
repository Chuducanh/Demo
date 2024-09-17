package com.example.pbl7_backend.controller;

import com.example.pbl7_backend.dto.ApiResponse;
import com.example.pbl7_backend.dto.user.UserDTO;
import com.example.pbl7_backend.dto.user.UserResponse;
import com.example.pbl7_backend.model.UserEntity;
import com.example.pbl7_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;

    @GetMapping("/me")
    public ResponseEntity<?> authenticatedUser() {
        UserDTO resBody = mapper.map(userService.getCurrentUser(), UserDTO.class);

        return ResponseEntity.ok(resBody);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();

        List<UserResponse> resBody = users.stream()
                .map(user -> mapper.map(user, UserResponse.class))
                .toList();

        return ResponseEntity.ok(resBody);
    }

    @PutMapping("/me/update")
    public ResponseEntity<?> updateNameUser(@RequestBody String name) {
        userService.updateUserName(name);
        return ResponseEntity.ok("Update successfully");
    }
}
