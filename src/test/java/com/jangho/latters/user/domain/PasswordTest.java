package com.jangho.latters.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {
    @Test
    @DisplayName("비밀번호 검증 성공")
    void validatePassword() {
        // given
        String password = "password1234";
    }

    @Test
    @DisplayName("비밀번호 검증 실패")
    void validatePasswordFail() {
        // given
        String password1 = "1234";
        String password2 = "password";
    }

    @Test
    @DisplayName("비밀번호 일치")
    void matchPassword() {
        // given
        String password = "password1234";
        String rawPassword = "password1234";
    }

    @Test
    @DisplayName("비밀번호 불일치")
    void matchPasswordFail() {
        // given
        String password = "password1234";
        String rawPassword = "password12345";
    }

}