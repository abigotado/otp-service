package org.abigotado.otp_service.features.otp.service;

import lombok.RequiredArgsConstructor;
import org.abigotado.otp_service.features.auth.model.User;
import org.abigotado.otp_service.features.otp.delivery.email.EmailOtpSender;
import org.abigotado.otp_service.features.otp.delivery.file.FileOtpSender;
import org.abigotado.otp_service.features.otp.delivery.sms.SmsOtpSender;
import org.abigotado.otp_service.features.otp.delivery.telegram.TelegramOtpSender;
import org.abigotado.otp_service.features.otp.dto.OtpCodeResponse;
import org.abigotado.otp_service.features.otp.model.DeliveryChannelType;
import org.abigotado.otp_service.features.otp.model.OtpCode;
import org.abigotado.otp_service.features.otp.model.OtpCodeStatus;
import org.abigotado.otp_service.features.otp.repository.OtpCodeRepository;
import org.abigotado.otp_service.features.otp_config.model.OtpConfig;
import org.abigotado.otp_service.features.otp_config.service.OtpConfigService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OtpCodeService {

    private final OtpCodeRepository repository;
    private final OtpConfigService configService;
    private final EmailOtpSender emailOtpSender;
    private final SmsOtpSender smsOtpSender;
    private final TelegramOtpSender telegramOtpSender;
    private final FileOtpSender fileOtpSender;

    public OtpCodeResponse generateCode(
            UUID userId, UUID operationId, @Nullable DeliveryChannelType channelType, @Nullable String recipient
    )
    {
        OtpConfig config = configService.getCurrentConfig();
        String code = generateNumericCode(config.getCodeLength());

        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(config.getTtlSeconds());

        OtpCode otp = OtpCode
                .builder()
                .code(code)
                .status(OtpCodeStatus.ACTIVE)
                .createdAt(now)
                .expiresAt(expiresAt)
                .operationId(operationId)
                .user(User.builder().id(userId).build()) // только ID, не подгружаем юзера
                .build();

        otp = repository.save(otp);

            switch (channelType) {
                case EMAIL -> emailOtpSender.send(otp.getCode(), recipient);
                case SMS -> smsOtpSender.send(otp.getCode(), recipient);
                case TELEGRAM -> telegramOtpSender.send(otp.getCode(), recipient);
                case null, default -> fileOtpSender.send(otp.getCode(), recipient);
            }

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