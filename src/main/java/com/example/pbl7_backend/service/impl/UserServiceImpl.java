package com.example.pbl7_backend.service.impl;

import com.example.pbl7_backend.model.UserEntity;
import com.example.pbl7_backend.repository.UserRepository;
import com.example.pbl7_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SongRepository songRepository;


    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) authentication.getPrincipal();
    }

    @Override
    public void updateUserName(String name) {
        UserEntity userEntity = getCurrentUser();
        userEntity.setName(name);
        userRepository.save(userEntity);
    }
}
