package org.abigotado.otp_service.features.otp_config.dto;

import lombok.Data;

@Data
public class OtpConfigRequest {
    private Integer codeLength;
    private Integer ttlSeconds;
}