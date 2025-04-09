package org.abigotado.otp_service.features.otp.service;

import lombok.RequiredArgsConstructor;
import org.abigotado.otp_service.features.auth.model.User;
import org.abigotado.otp_service.features.otp.dto.OtpCodeResponse;
import org.abigotado.otp_service.features.otp.model.OtpCode;
import org.abigotado.otp_service.features.otp.model.OtpCodeStatus;
import org.abigotado.otp_service.features.otp.repository.OtpCodeRepository;
import org.abigotado.otp_service.features.otp_config.model.OtpConfig;
import org.abigotado.otp_service.features.otp_config.service.OtpConfigService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OtpCodeService {

    private final OtpCodeRepository repository;
    private final OtpConfigService configService;

    public OtpCodeResponse generateCode(UUID userId, UUID operationId) {
        OtpConfig config = configService.getCurrentConfig();
        String code = generateNumericCode(config.getCodeLength());

        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(config.getTtlSeconds());

        OtpCode otp = OtpCode.builder()
                             .code(code)
                             .status(OtpCodeStatus.ACTIVE)
                             .createdAt(now)
                             .expiresAt(expiresAt)
                             .operationId(operationId)
                             .user(User.builder().id(userId).build()) // только ID, не подгружаем юзера
                             .build();

        otp = repository.save(otp);

        return new OtpCodeResponse(otp.getId(), otp.getCode(), otp.getExpiresAt(), otp.getStatus());
    }

    private String generateNumericCode(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}