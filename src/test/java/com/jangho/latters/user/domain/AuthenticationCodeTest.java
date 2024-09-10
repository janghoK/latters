package com.jangho.latters.user.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationCodeTest {

    @Test
    @DisplayName("회원가입 이메일 인증코드를 생성한다.")
    void generate() {
        Email givenEmail = Email.from("test@example.com");
        AuthenticationCode givenCode = AuthenticationCode.generate(givenEmail);

        Assertions.assertAll(
                () -> Assertions.assertNotNull(givenCode.email()),
                () -> Assertions.assertNotNull(givenCode.verificationCode())
        );
    }

    @Test
    @DisplayName("인증코드를 검증한다.")
    void verifyVerificationCode() {
        Email givenEmail = Email.from("test@example.com");
        VerificationCode givenVerificationCode = VerificationCode.generate();
        AuthenticationCode givenCode = new AuthenticationCode(givenEmail, givenVerificationCode);

        Assertions.assertDoesNotThrow(() -> givenCode.verifyVerificationCode(givenVerificationCode.getVerificationCode()));
    }

}