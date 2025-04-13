package org.abigotado.otp_service.features.otp.repository;

import org.abigotado.otp_service.features.otp.model.OtpCode;
import org.abigotado.otp_service.features.otp.model.OtpCodeStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface OtpCodeRepository extends JpaRepository<OtpCode, UUID> {
    List<OtpCode> findByUserId(UUID userId);
    List<OtpCode> findAllByStatusAndExpiresAtBefore(OtpCodeStatus status, Instant timestamp);
}