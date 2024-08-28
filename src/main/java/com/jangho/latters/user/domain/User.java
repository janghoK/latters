package com.jangho.latters.user.domain;

import lombok.Getter;

import java.time.Instant;

@Getter
public class User {
    private Long id;
    private String email;
    private String password;

    private String name;

    private Instant lastLoginAt;
    private String verificationCode;
    private Instant verificationCodeExpiresIn;
    private Instant activatedAt;
}
