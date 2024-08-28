package com.jangho.latters.user.application.port.in;

import com.jangho.latters.user.domain.User;

public interface UserUseCase {
    User findById(Long id);
    User findByEmail(String email);

    void sendAuthenticationCode(String email);
    void sendVerificationCode(String email);
    void join(User user, String authenticationCode);
    User updateProfile(Long id, String name);
    void resetPassword(User user, String verificationCode);
    void updatePassword(Long id, User user, String currentPassword);
    void withdrawal(Long id, String reason);
}
