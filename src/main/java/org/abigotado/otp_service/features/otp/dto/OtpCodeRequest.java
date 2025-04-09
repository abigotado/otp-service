package org.abigotado.otp_service.features.otp.dto;

import java.util.UUID;
import jakarta.annotation.Nullable;

public record OtpCodeRequest(
    @Nullable UUID operationId
) {}