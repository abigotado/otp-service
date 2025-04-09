package org.abigotado.otp_service.features.otp_config.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "otp_config")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OtpConfig {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private int codeLength;

    @Column(nullable = false)
    private int ttlSeconds; // Time to live in seconds
}