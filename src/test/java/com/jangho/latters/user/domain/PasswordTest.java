package com.jangho.latters.user.domain;

import com.jangho.latters.common.exception.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class PasswordTest {

    PasswordEncoder givenPasswordEncoder = new BCryptPasswordEncoder();

    @Test
    @DisplayName("비밀번호 검증 성공")
    void validatePassword() {
        String rawPassword = "password1234";

        Password password = Password.of(rawPassword, givenPasswordEncoder);
        password.validate();

        Assertions.assertDoesNotThrow(password::validate);
    }

    @Test
    @DisplayName("비밀번호 검증 실패")
    void validatePasswordFail() {
        String rawPassword1 = "1234";
        String rawPassword2 = "invalid";

        Password password1 = Password.of(rawPassword1, givenPasswordEncoder);
        Password password2 = Password.of(rawPassword2, givenPasswordEncoder);

        Assertions.assertAll(
                () -> Assertions.assertThrows(CustomException.class, password1::validate),
                () -> Assertions.assertThrows(CustomException.class, password2::validate)
        );
    }

    @Test
    @DisplayName("비밀번호 일치")
    void matchPassword() {
        // given
        String savedPassword = "password1234";
        String enteredPassword = "password1234";

        Password password = Password.of(savedPassword, givenPasswordEncoder);


        Assertions.assertAll(
                () -> Assertions.assertDoesNotThrow(password::validate),
                () -> Assertions.assertDoesNotThrow(() -> password.match(enteredPassword)),
                () -> Assertions.assertTrue(password.match(enteredPassword))
        );
    }

    @Test
    @DisplayName("비밀번호 불일치")
    void matchPasswordFail() {
        // given
        String password = "password1234";
        String rawPassword = "password12345";

        Password givenPassword = Password.of(password, givenPasswordEncoder);

        Assertions.assertAll(
                () -> Assertions.assertDoesNotThrow(() -> givenPassword.match(rawPassword)),
                () -> Assertions.assertFalse(givenPassword.match(rawPassword))
        );
    }

    @Test
    @DisplayName("비밀번호 변경 성공")
    void changePassword() {
        // given
        String savedPassword = "password1234";
        String newPassword = "newPassword1234";

        Password password = Password.of(savedPassword, givenPasswordEncoder);

        Assertions.assertAll(
                () -> Assertions.assertDoesNotThrow(password::validate),
                () -> Assertions.assertDoesNotThrow(() -> password.changePassword(savedPassword, newPassword)),
                () -> Assertions.assertTrue(password.match(newPassword))
        );
    }

    @Test
    @DisplayName("비밀번호 변경 실패")
    void changePasswordFail() {
        // given
        String savedPassword = "password1234";
        String wrongPassword = "wrong";

        Password password = Password.of(savedPassword, givenPasswordEncoder);

        Assertions.assertAll(
                () -> Assertions.assertDoesNotThrow(password::validate),
                () -> Assertions.assertThrows(CustomException.class, () -> password.changePassword(wrongPassword, "newPassword1234"))
        );
    }

    @Test
    @DisplayName("password encoder가 null이면 오류를 반환한다.")
    void passwordEncoderIsNull() {
        // given
        String rawPassword = "password1234";
        PasswordEncoder passwordEncoder = null;
        Password invalidPassword = Password.of(rawPassword, passwordEncoder);

        Assertions.assertAll(
                () -> Assertions.assertThrows(CustomException.class, invalidPassword::validate),
                () -> Assertions.assertThrows(CustomException.class, () ->
                        invalidPassword.changePassword(rawPassword, "newPassword1234"))
        );
    }

}