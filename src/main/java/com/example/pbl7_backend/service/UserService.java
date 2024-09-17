package com.example.pbl7_backend.service;

import com.example.pbl7_backend.model.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> getAllUsers();
    UserEntity getCurrentUser();
    void updateUserName(String name);
}
