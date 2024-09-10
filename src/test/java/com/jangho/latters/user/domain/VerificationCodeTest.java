package com.jangho.latters.user.domain;

import com.jangho.latters.common.exception.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class VerificationCodeTest {

    @Test
    @DisplayName("인증 코드가 만료되었거나 코드가 일치하지 않을 때 오류를 반환한다.")
    void validateExpiredVerificationCode() {
        String verificationCode = "1234";
        String wrongCode = "4321";
        Instant expired = Instant.now().minusSeconds(1);
        Instant notExpired = Instant.now().plusSeconds(60);
        VerificationCode expiredCode = VerificationCode.of(verificationCode, expired);
        VerificationCode validCode = VerificationCode.of(verificationCode, notExpired);

        Assertions.assertAll(
                () -> Assertions.assertThrows(CustomException.class, () -> expiredCode.validate(verificationCode)),
                () -> Assertions.assertThrows(CustomException.class, () -> validCode.validate(wrongCode))
        );
    }

    @Test
    @DisplayName("인증 코드가 만료되지 않았고 코드가 일치할 때 오류를 반환하지 않는다.")
    void validateValidVerificationCode() {
        String verificationCode = "1234";
        Instant notExpired = Instant.now().plusSeconds(60);
        VerificationCode validCode = VerificationCode.of(verificationCode, notExpired);

        Assertions.assertDoesNotThrow(() -> validCode.validate(verificationCode));
    }

}
