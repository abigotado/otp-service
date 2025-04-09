package org.abigotado.otp_service.features.otp.controller;

import lombok.RequiredArgsConstructor;
import org.abigotado.otp_service.features.auth.model.User;
import org.abigotado.otp_service.features.otp.dto.OtpCodeRequest;
import org.abigotado.otp_service.features.otp.dto.OtpCodeResponse;
import org.abigotado.otp_service.features.otp.service.OtpCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OtpCodeController {

    private final OtpCodeService otpService;

    @PostMapping("/generate")
    public ResponseEntity<OtpCodeResponse> generate(
            @RequestBody(required = false) Optional<OtpCodeRequest> request,
            @AuthenticationPrincipal User user
    ) {
        var response = otpService.generateCode(user.getId(), request.map(OtpCodeRequest::operationId).orElse(null));
        return ResponseEntity.ok(response);
    }
}