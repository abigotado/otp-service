package org.abigotado.otp_service.features.otp_config.service;

import lombok.RequiredArgsConstructor;
import org.abigotado.otp_service.features.otp_config.dto.OtpConfigRequest;
import org.abigotado.otp_service.features.otp_config.model.OtpConfig;
import org.abigotado.otp_service.features.otp_config.repository.OtpConfigRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OtpConfigService {

    private final OtpConfigRepository repository;

    public Optional<OtpConfig> findCurrentConfig() {
        return repository.findAll().stream().findFirst();
    }

    public OtpConfig getCurrentConfig() {
        return findCurrentConfig()
                .orElseThrow(() -> new IllegalStateException("OTP config not initialized"));
    }

    public OtpConfig updateConfig(OtpConfigRequest request) {
        List<OtpConfig> configs = repository.findAll();
        OtpConfig config = configs.isEmpty() ? new OtpConfig() : configs.getFirst();

        if (request.getCodeLength() != null) {
            config.setCodeLength(request.getCodeLength());
        }
        if (request.getTtlSeconds() != null) {
            config.setTtlSeconds(request.getTtlSeconds());
        }

        return repository.save(config);
    }
}