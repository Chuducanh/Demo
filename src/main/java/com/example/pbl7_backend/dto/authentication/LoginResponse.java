package com.example.pbl7_backend.dto.authentication;

import com.example.pbl7_backend.dto.user.UserDTO;
import com.example.pbl7_backend.model.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoginResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("user")
    private UserDTO userDTO;
    @JsonProperty("expires_at")
    private Date expiresAt;
}
