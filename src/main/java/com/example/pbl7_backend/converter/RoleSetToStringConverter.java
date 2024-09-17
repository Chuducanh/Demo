package com.example.pbl7_backend.converter;

import com.example.pbl7_backend.model.Role;
import org.modelmapper.AbstractConverter;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleSetToStringConverter extends AbstractConverter<Set<Role>, Set<String>> {
    @Override
    protected Set<String> convert(Set<Role> roles) {
        return roles.stream()
                .map(role -> role.getName().toString())
                .collect(Collectors.toSet());
    }
}
