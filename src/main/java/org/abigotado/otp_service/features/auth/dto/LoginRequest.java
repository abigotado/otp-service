package org.abigotado.otp_service.features.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}