package org.abigotado.otp_service.features.otp.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.abigotado.otp_service.features.otp.model.OtpCode;
import org.abigotado.otp_service.features.otp.model.OtpCodeStatus;
import org.abigotado.otp_service.features.otp.repository.OtpCodeRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OtpExpirationScheduler {

    private final OtpCodeRepository repository;

    @Scheduled(fixedDelay = 60000) // каждые 60 секунд
    public void expireCodes() {
        Instant now = Instant.now();
        List<OtpCode> expiredCodes = repository.findAllByStatusAndExpiresAtBefore(OtpCodeStatus.ACTIVE, now);

        if (!expiredCodes.isEmpty()) {
            log.info("⏰ Просрочено кодов: {}", expiredCodes.size());
            expiredCodes.forEach(code -> code.setStatus(OtpCodeStatus.EXPIRED));
            repository.saveAll(expiredCodes);
        }
    }
}