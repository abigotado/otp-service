package org.abigotado.otp_service.features.otp.repository;

import org.abigotado.otp_service.features.otp.model.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OtpCodeRepository extends JpaRepository<OtpCode, UUID> {
    List<OtpCode> findByUserId(UUID userId);
}