package com.jangho.latters.user.application.service;

import com.jangho.latters.common.exception.CustomException;
import com.jangho.latters.common.model.enums.ResponseCode;
import com.jangho.latters.user.application.port.in.UserUseCase;
import com.jangho.latters.user.application.port.out.AuthenticationCodePersistencePort;
import com.jangho.latters.user.application.port.out.UserPersistencePort;
import com.jangho.latters.user.domain.AuthenticationCode;
import com.jangho.latters.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {

    private final UserPersistencePort userPersistencePort;
    private final AuthenticationCodePersistencePort authenticationCodePersistencePort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findById(Long id) {
        return userPersistencePort.findById(id)
                .orElseThrow(() -> new CustomException(ResponseCode.FAIL, "사용자를 찾을 수 없습니다."));
    }

    @Override
    public User findByEmail(String email) {
        return userPersistencePort.findByEmail(email)
                .orElseThrow(() -> new CustomException(ResponseCode.FAIL, "사용자를 찾을 수 없습니다."));
    }

    @Override
    @Transactional
    public void sendAuthenticationCode(String email) {
        AuthenticationCode authenticationCode = AuthenticationCode.generate(email);

        // @TODO: Send verification code to email
        authenticationCodePersistencePort.create(authenticationCode);
    }

    @Override
    @Transactional
    public void sendVerificationCode(String email) {
        User existed = userPersistencePort.findByEmail(email)
                .orElseThrow(() -> new CustomException(ResponseCode.FAIL, "사용자를 찾을 수 없습니다."));
        existed.generateVerificationCode();

        // @TODO: Send verification code to email
        userPersistencePort.update(existed.getId(), existed);
    }

    @Override
    @Transactional
    public User join(String email, String rawPassword, String name, String authenticationCode) {
        AuthenticationCode existedCode = authenticationCodePersistencePort.findByEmail(email)
                .orElseThrow(() -> new CustomException(ResponseCode.FAIL, "인증 코드를 찾을 수 없습니다."));

        existedCode.verifyVerificationCode(authenticationCode);

        User user = User.of(email, rawPassword, passwordEncoder, name);
        user.join();

        authenticationCodePersistencePort.delete(existedCode.id());
        return userPersistencePort.create(user);
    }

    @Override
    @Transactional
    public User updateProfile(Long id, String name) {
        User existed = userPersistencePort.findById(id)
                .orElseThrow(() -> new CustomException(ResponseCode.FAIL, "사용자를 찾을 수 없습니다."));

        existed.updateName(name);
        return userPersistencePort.update(id, existed);
    }

    @Override
    @Transactional
    public User resetPassword(String email, String newPassword, String verificationCode) {
        User existed = userPersistencePort.findByEmail(email)
                .orElseThrow(() -> new CustomException(ResponseCode.FAIL, "사용자를 찾을 수 없습니다."));

        existed.verifyVerificationCode(verificationCode);
        existed.resetPassword(passwordEncoder, newPassword);
        return userPersistencePort.update(existed.getId(), existed);
    }

    @Override
    @Transactional
    public User updatePassword(Long id, String currentPassword, String newPassword) {
        User existed = userPersistencePort.findById(id)
                .orElseThrow(() -> new CustomException(ResponseCode.FAIL, "사용자를 찾을 수 없습니다."));

        existed.changePassword(passwordEncoder, currentPassword, newPassword);
        return userPersistencePort.update(existed.getId(), existed);
    }

    @Override
    @Transactional
    public void withdrawal(Long id) {
        userPersistencePort.delete(id);
    }
}
