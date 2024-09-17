package com.example.pbl7_backend.bootstrap;

import com.example.pbl7_backend.model.Role;
import com.example.pbl7_backend.model.RoleEnum;
import com.example.pbl7_backend.model.UserEntity;
import com.example.pbl7_backend.repository.RoleRepository;
import com.example.pbl7_backend.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@Order(2)
@RequiredArgsConstructor
public class UserSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
//        loadUsers();
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }

    private void loadUsers() {
        List<UserEntity> users = new ArrayList<>();
        Optional<Role> roleAdmin = roleRepository.findByName(RoleEnum.ADMIN);
        roleAdmin.ifPresent(role -> users.add(UserEntity.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles(Set.of(role))
                .build()));

        Optional<Role> roleUser = roleRepository.findByName(RoleEnum.USER);
        roleUser.ifPresent(role -> {
            for (int i = 0; i < 10; i++) {
                users.add(UserEntity.builder()
                        .username("user" + i)
                        .password(passwordEncoder.encode("secret"))
                        .roles(Set.of(role))
                        .build());
            }
        });

        userRepository.saveAll(users);
    }
}
