package org.abigotado.otp_service.features.otp.model;

import jakarta.persistence.*;
import lombok.*;
import org.abigotado.otp_service.features.auth.model.User;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "otp_codes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OtpCode {

    @Id
    @GeneratedValue
    private UUID id;

    private String code;

    @Enumerated(EnumType.STRING)
    private OtpCodeStatus status;

    private Instant createdAt;

    private Instant expiresAt;

    private UUID operationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}