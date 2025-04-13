package org.abigotado.otp_service.features.user.service;

import lombok.RequiredArgsConstructor;
import org.abigotado.otp_service.features.auth.repository.UserRepository;
import org.abigotado.otp_service.features.otp.repository.OtpCodeRepository;
import org.abigotado.otp_service.features.user.dto.UserResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAdminService {

    private final UserRepository userRepository;
    private final OtpCodeRepository otpCodeRepository;

    public List<UserResponseDto> getAllNonAdminUsers() {
        return userRepository.findAllNonAdminUsers().stream()
                             .map(user -> new UserResponseDto(user.getId(), user.getUsername(), user.getRole()))
                             .toList();
    }

    @Transactional
    public void deleteUserAndCodesById(UUID userId) {
        otpCodeRepository.deleteAllByUserId(userId);
        userRepository.deleteById(userId);
    }

}