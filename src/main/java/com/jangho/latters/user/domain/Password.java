package com.jangho.latters.user.domain;

import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class Password implements Credential {

    private String password;
    private final PasswordEncoder passwordEncoder;

    private Password(String password, PasswordEncoder passwordEncoder) {
        this.password = password;
        this.passwordEncoder = passwordEncoder;
    }

    public static Password of(String password, PasswordEncoder passwordEncoder) {
        return new Password(password, passwordEncoder);
    }

    public boolean match(String rawPassword) {
        return passwordEncoder.matches(rawPassword, password);
    }

    public void change(String newPassword) {
        this.password = newPassword;
        this.validate();
    }

    @Override
    public void validate() {
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("비밀번호는 8자 이상이어야 합니다.");
        }

        this.encode();
    }

    private void encode() {
        this.password = passwordEncoder.encode(password);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }




}
