package org.abigotado.otp_service.features.otp_config.repository;

import org.abigotado.otp_service.features.otp_config.model.OtpConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OtpConfigRepository extends JpaRepository<OtpConfig, UUID> {
}