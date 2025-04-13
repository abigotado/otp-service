package org.abigotado.otp_service.features.user.controller;

import lombok.RequiredArgsConstructor;
import org.abigotado.otp_service.features.user.dto.UserResponseDto;
import org.abigotado.otp_service.features.user.service.UserAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserAdminController {

    private final UserAdminService userAdminService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllNonAdminUsers() {
        return ResponseEntity.ok(userAdminService.getAllNonAdminUsers());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userAdminService.deleteUserAndCodesById(userId);
        return ResponseEntity.noContent().build();
    }
}