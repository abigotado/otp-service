package org.abigotado.otp_service.features.user.dto;

import org.abigotado.otp_service.features.auth.model.User;

import java.util.UUID;

public record UserResponseDto(UUID id, String username, User.Role role) {}