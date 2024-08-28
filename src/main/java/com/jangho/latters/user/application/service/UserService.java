package com.jangho.latters.user.application.service;

import com.jangho.latters.user.application.port.in.UserUseCase;
import com.jangho.latters.user.application.port.out.UserPersistencePort;
import com.jangho.latters.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {

    private final UserPersistencePort userPersistencePort;

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public void sendAuthenticationCode(String email) {

    }

    @Override
    public void sendVerificationCode(String email) {

    }

    @Override
    public void join(User user, String authenticationCode) {

    }

    @Override
    public User updateProfile(Long id, String name) {
        return null;
    }

    @Override
    public void resetPassword(User user, String verificationCode) {

    }

    @Override
    public void updatePassword(Long id, User user, String currentPassword) {

    }

    @Override
    public void withdrawal(Long id, String reason) {

    }
}
