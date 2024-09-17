package com.example.pbl7_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ApiResponse {
    private boolean success;
    private String message;
    private Map<String, Object> errorList = new HashMap<>();
}
