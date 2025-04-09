package org.abigotado.otp_service.features.auth.controller;

import org.abigotado.otp_service.features.auth.dto.LoginRequest;
import org.abigotado.otp_service.features.auth.dto.RegisterRequest;
import org.abigotado.otp_service.features.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}