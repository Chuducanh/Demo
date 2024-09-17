package com.example.pbl7_backend.bootstrap;

import com.example.pbl7_backend.model.Role;
import com.example.pbl7_backend.model.RoleEnum;
import com.example.pbl7_backend.repository.RoleRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
@Order(1)
@RequiredArgsConstructor
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;


    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        loadRoles();
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }

    private void loadRoles() {
        RoleEnum[] roleNames = new RoleEnum[]{
                RoleEnum.USER, RoleEnum.ADMIN
        };
        Arrays.stream(roleNames).forEach(roleName -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = Role.builder()
                        .name(roleName)
                        .build();
                roleRepository.save(roleToCreate);
            });
        });
    }
}
