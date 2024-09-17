package com.example.pbl7_backend.service.impl;

import com.example.pbl7_backend.dto.authentication.LoginRequest;
import com.example.pbl7_backend.dto.authentication.RegisterRequest;
import com.example.pbl7_backend.exception.RoleNotFoundException;
import com.example.pbl7_backend.exception.UsernameExistedException;
import com.example.pbl7_backend.model.Role;
import com.example.pbl7_backend.model.RoleEnum;
import com.example.pbl7_backend.model.UserEntity;
import com.example.pbl7_backend.repository.RoleRepository;
import com.example.pbl7_backend.repository.UserRepository;
import com.example.pbl7_backend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public UserEntity authenticate(final LoginRequest dto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dto.getUsername(),
                dto.getPassword()
        ));
        return userRepository.findByUsername(dto.getUsername())
                .orElseThrow();
    }

    @Override
    public UserEntity register(final RegisterRequest dto) {
        // Check duplicate username
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new UsernameExistedException();
        }

        // Check role existence
        Optional<Role> roleOptional = roleRepository.findByName(RoleEnum.valueOf(dto.getRole()));
        if (roleOptional.isEmpty()) {
            throw new RoleNotFoundException();
        }

        UserEntity user = UserEntity.builder()
                .username(dto.getUsername())
                .name(dto.getName())
                .password(passwordEncoder.encode(dto.getPassword()))
                .roles(Set.of(roleOptional.get()))
                .build();

        return userRepository.save(user);
    }
}
