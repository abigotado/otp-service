package org.abigotado.otp_service.features.otp.dto;

import java.util.UUID;

public record OtpCodeRequest(
    UUID operationId
) {}