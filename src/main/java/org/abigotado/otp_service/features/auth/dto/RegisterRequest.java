package org.abigotado.otp_service.features.auth.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "password")
public class RegisterRequest {
    private String username;
    private String password;
}