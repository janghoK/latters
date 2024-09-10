package com.jangho.latters.user.domain;

import com.jangho.latters.common.exception.CustomException;
import com.jangho.latters.common.model.enums.ResponseCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.time.Instant;

@Getter
public class User {
    private final Long id;
    private final Email email;
    private final Password password;

    private String name;

    private Instant lastLoginAt;
    private Instant activatedAt;
    private VerificationCode verificationCode;

    @Builder
    public User(Long id, Email email, Password password, String name, Instant lastLoginAt, VerificationCode verificationCode, Instant activatedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastLoginAt = lastLoginAt;
        this.verificationCode = verificationCode;
        this.activatedAt = activatedAt;
    }

    public static User of(String email, String password, PasswordEncoder passwordEncoder, String name) {
        return User.builder()
                .email(Email.from(email))
                .password(Password.of(password, passwordEncoder))
                .name(name)
                .build();
    }

    public void join() {
        this.email.validate();
        this.password.validate();
        this.updateName(this.name);

        this.activatedAt = Instant.now();
    }

    public void updateName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new CustomException(ResponseCode.FAIL, "이름은 필수입니다.");
        } else if (name.length() > 20) {
            throw new CustomException(ResponseCode.FAIL, "이름은 20자 이하로 입력해주세요.");
        } else if (name.length() < 2) {
            throw new CustomException(ResponseCode.FAIL, "이름은 2자 이상 입력해주세요.");
        }

        this.name = name;
    }

    public void changePassword(PasswordEncoder passwordEncoder, String currentPassword, String newPassword) {
        this.password.setPasswordEncoder(passwordEncoder);
        this.password.changePassword(currentPassword, newPassword);
    }

    public void generateVerificationCode() {
        this.verificationCode = VerificationCode.generate();
    }

    public void verifyVerificationCode(String code) {
        this.verificationCode.validate(code);
        this.verificationCode = null;
    }

    public void login() {
        this.lastLoginAt = Instant.now();
    }

    public String getEmail() {
        return this.email.getEmail();
    }
}
