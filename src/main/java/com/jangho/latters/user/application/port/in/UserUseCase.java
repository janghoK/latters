package com.jangho.latters.user.application.port.in;

import com.jangho.latters.user.domain.User;

public interface UserUseCase {
    User findById(Long id);
    User findByEmail(String email);

    void sendAuthenticationCode(String email);
    void sendVerificationCode(String email);
    User join(String email, String rawPassword, String name, String authenticationCode);
    User updateProfile(Long id, String name);
    User resetPassword(String email, String newPassword, String verificationCode);
    User updatePassword(Long id, String currentPassword, String newPassword);
    void withdrawal(Long id);
}
