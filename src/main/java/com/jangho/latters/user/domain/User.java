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
    private Long id;
    private Credential email;
    private Credential password;

    private String name;

    private Instant lastLoginAt;
    private String verificationCode;
    private Instant verificationCodeExpiresIn;
    private Instant activatedAt;

    @Builder
    public User(Long id, Credential email, Credential password, String name, Instant lastLoginAt, String verificationCode, Instant verificationCodeExpiresIn, Instant activatedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastLoginAt = lastLoginAt;
        this.verificationCode = verificationCode;
        this.verificationCodeExpiresIn = verificationCodeExpiresIn;
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
        
        this.activatedAt = Instant.now();
    }

    public void updateName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new CustomException(ResponseCode.FAIL, "이름은 필수입니다.");
        }

        this.name = name;
    }
}
