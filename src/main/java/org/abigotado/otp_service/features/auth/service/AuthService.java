package org.abigotado.otp_service.features.auth.service;

import org.abigotado.otp_service.features.auth.dto.LoginRequest;
import org.abigotado.otp_service.features.auth.dto.RegisterRequest;
import org.abigotado.otp_service.features.auth.model.User;
import org.abigotado.otp_service.features.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void register(RegisterRequest request) {
        boolean adminExists = userRepository.existsByRole(User.Role.ADMIN);
        User.Role role = adminExists ? User.Role.USER : User.Role.ADMIN;

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtService.generateToken(user);
    }
}