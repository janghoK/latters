package com.jangho.latters.user.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class EmailTest {
    @Test
    @DisplayName("이메일 검증 성공")
    void validateEmail() {
        String email = "test@example.com";

        Assertions.assertDoesNotThrow(() -> Email.from(email).validate());
    }

    @Test
    @DisplayName("이메일 검증 실패")
    void validateEmailFail() {
        // given
        String email1 = "test@example";
        String email2 = "testexample.com";

        Assertions.assertAll(
                () -> Assertions.assertThrows(Exception.class, () -> Email.from(email1).validate()),
                () -> Assertions.assertThrows(Exception.class, () -> Email.from(email2).validate())
        );
    }

}