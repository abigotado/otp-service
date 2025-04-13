package org.abigotado.otp_service.features.otp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OtpCodeValidationRequest(
    @NotNull UUID operationId,
    @NotBlank String code
) {}