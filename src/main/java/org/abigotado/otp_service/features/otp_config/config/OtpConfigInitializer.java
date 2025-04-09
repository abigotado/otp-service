package org.abigotado.otp_service.features.otp_config.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.abigotado.otp_service.features.otp_config.model.OtpConfig;
import org.abigotado.otp_service.features.otp_config.repository.OtpConfigRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OtpConfigInitializer {

    private final OtpConfigRepository repository;

    @PostConstruct
    public void ensureConfigExists() {
        if (repository.count() == 0) {
            OtpConfig defaultConfig = new OtpConfig();
            defaultConfig.setCodeLength(6);      // по умолчанию 6 цифр
            defaultConfig.setTtlSeconds(300);    // по умолчанию 5 минут
            repository.save(defaultConfig);      // Hibernate сам установит UUID
        }
    }
}