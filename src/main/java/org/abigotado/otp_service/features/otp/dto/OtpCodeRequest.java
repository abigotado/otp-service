package org.abigotado.otp_service.features.otp.dto;

import jakarta.annotation.Nullable;
import org.abigotado.otp_service.features.otp.model.DeliveryChannelType;

import java.util.UUID;

public record OtpCodeRequest(
        @Nullable UUID operationId,
        @Nullable DeliveryChannelType channel,
        @Nullable String recipient
) {}