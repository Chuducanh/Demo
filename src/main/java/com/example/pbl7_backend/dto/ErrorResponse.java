package com.example.pbl7_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorResponse {
    @JsonProperty("title")
    private String title;
    @JsonProperty("status")
    private int status;
    @JsonProperty("validation_errors")
    private Map<String, String> validationErrors;
    @JsonProperty("error_message")
    private String errorMessage;
    @JsonProperty("path")
    private String path;
}
