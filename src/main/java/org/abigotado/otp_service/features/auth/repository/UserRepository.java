package org.abigotado.otp_service.features.auth.repository;

import org.abigotado.otp_service.features.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    boolean existsByRole(User.Role role);
    @Query("SELECT u FROM User u WHERE u.role <> 'ADMIN'")
    List<User> findAllNonAdminUsers();
}