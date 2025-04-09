package org.abigotado.otp_service.features.otp.dto;

import org.abigotado.otp_service.features.otp.model.OtpCodeStatus;

import java.time.Instant;
import java.util.UUID;

public record OtpCodeResponse(
    UUID id,
    String code,
    Instant expiresAt,
    OtpCodeStatus status
) {}