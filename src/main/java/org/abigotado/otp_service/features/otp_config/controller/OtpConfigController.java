package org.abigotado.otp_service.features.otp_config.controller;

import lombok.RequiredArgsConstructor;
import org.abigotado.otp_service.features.otp_config.dto.OtpConfigRequest;
import org.abigotado.otp_service.features.otp_config.model.OtpConfig;
import org.abigotado.otp_service.features.otp_config.service.OtpConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/config")
@RequiredArgsConstructor
public class OtpConfigController {

    private final OtpConfigService service;

    @GetMapping
    public ResponseEntity<OtpConfig> getConfig() {
        return ResponseEntity.ok(service.getCurrentConfig());
    }

    @PatchMapping
    public ResponseEntity<OtpConfig> patchConfig(@RequestBody OtpConfigRequest request) {
        return ResponseEntity.ok(service.updateConfig(request));
    }
}