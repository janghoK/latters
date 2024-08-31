package com.jangho.latters.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {
    @Test
    @DisplayName("이메일 검증 성공")
    void validateEmail() {
        // given
        String email = "test@example.com";
    }

    @Test
    @DisplayName("이메일 검증 실패")
    void validateEmailFail() {
        // given
        String email1 = "test@example";
        String email2 = "testexample.com";
    }

}