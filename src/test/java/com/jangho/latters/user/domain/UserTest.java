package com.jangho.latters.user.domain;

import com.jangho.latters.common.exception.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;


class UserTest {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    User joinedUser;

    @BeforeEach
    void beforeEach() {
        String givenEmail = "test@example.com";
        String givenPassword = "password";
        String givenName = "John Doe";
        PasswordEncoder givenPasswordEncoder = new BCryptPasswordEncoder();
        joinedUser = User.of(givenEmail, givenPassword, givenPasswordEncoder, givenName);
        joinedUser.join();
    }

    @Test
    @DisplayName("회원가입 성공 케이스")
    void createUser() {
        String givenEmail = "test@example.com";
        String givenPassword = "password";
        String givenName = "John Doe";
        PasswordEncoder givenPasswordEncoder = new BCryptPasswordEncoder();

        User givenUser = User.of(givenEmail, givenPassword, givenPasswordEncoder, givenName);

        Assertions.assertAll(
                () -> Assertions.assertDoesNotThrow(givenUser::join),
                () -> Assertions.assertEquals(givenEmail, givenUser.getEmail()),
                () -> Assertions.assertEquals(givenName, givenUser.getName()),
                () -> Assertions.assertNotNull(givenUser.getActivatedAt())
        );
    }

    @Test
    @DisplayName("회원가입 실패 케이스")
    void createUserFail() {
        String givenEmail = "testexample.com";
        String givenPassword = "password";
        String givenName = "John Doe";

        User givenUser = User.of(givenEmail, givenPassword, passwordEncoder, givenName);

        Assertions.assertAll(
                () -> Assertions.assertThrows(CustomException.class,
                        () -> User.of("testexample.com", "password", passwordEncoder, "John Doe").join()),
                () -> Assertions.assertThrows(CustomException.class,
                        () -> User.of("test@example.com", "invalid", passwordEncoder, "John Doe").join()),
                () -> Assertions.assertThrows(CustomException.class,
                        () -> User.of("test@example.com", "password", passwordEncoder, "j").join()),
                () -> Assertions.assertThrows(CustomException.class,
                        () -> User.of("test@example.com", "password", null, "John Doe").join())
        );
    }

    @Test
    @DisplayName("회원정보 이름 수정 성공 케이스")
    void updateUserName() {
        String givenName = "Foo bar";

        Assertions.assertAll(
                () -> Assertions.assertDoesNotThrow(() -> joinedUser.updateName(givenName)),
                () -> Assertions.assertEquals(givenName, joinedUser.getName())
        );
    }

    @Test
    @DisplayName("회원정보 이름 수정 실패 케이스")
    void updateUserFail() {
        String givenName = "F";

        Assertions.assertAll(
                () -> Assertions.assertThrows(CustomException.class, () -> joinedUser.updateName(givenName)),
                () -> Assertions.assertThrows(CustomException.class, () -> joinedUser.updateName(null)),
                () -> Assertions.assertThrows(CustomException.class, () -> joinedUser.updateName(""))
        );
    }

    @Test
    @DisplayName("비밀번호 변경 성공 케이스")
    void changePassword() {
        String currentPassword = "password";
        String newPassword = "newPassword";

        Assertions.assertDoesNotThrow(() -> joinedUser.changePassword(passwordEncoder, currentPassword, newPassword));
    }

    @Test
    @DisplayName("비밀변호 변경 실패 케이스")
    void changePasswordFail() {
        String currentPassword = "password";
        String newPassword = "newPassword";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Assertions.assertAll(
                () -> Assertions.assertThrows(CustomException.class, () -> joinedUser.changePassword(passwordEncoder, "invalid", newPassword)),
                () -> Assertions.assertThrows(CustomException.class, () -> joinedUser.changePassword(passwordEncoder, currentPassword, "invalid")),
                () -> Assertions.assertThrows(CustomException.class, () -> joinedUser.changePassword(passwordEncoder, currentPassword, null)),
                () -> Assertions.assertThrows(CustomException.class, () -> joinedUser.changePassword(passwordEncoder, null, newPassword)),
                () -> Assertions.assertThrows(CustomException.class, () -> joinedUser.changePassword(passwordEncoder, currentPassword, ""))
        );
    }

    @Test
    @DisplayName("인증코드를 생성한다.")
    void generateVerificationCode() {
        joinedUser.generateVerificationCode();

        Assertions.assertNotNull(joinedUser.getVerificationCode());
    }

    @Test
    @DisplayName("인증코드 검증 성공 케이스")
    void verifyVerificationCode() {
        VerificationCode verificationCode = VerificationCode.generate();
        String code = verificationCode.getVerificationCode();
        User givenUser = User.builder().verificationCode(verificationCode).build();

        Assertions.assertAll(
                () -> Assertions.assertDoesNotThrow(() -> givenUser.verifyVerificationCode(code)),
                () -> Assertions.assertNull(givenUser.getVerificationCode())
        );
    }

    @Test
    @DisplayName("인증코드 검증 실패 케이스")
    void verifyVerificationCodeFail() {
        VerificationCode verificationCode = VerificationCode.generate();
        String code = "abcdef";
        User givenUser = User.builder().verificationCode(verificationCode).build();

        Assertions.assertThrows(CustomException.class, () -> givenUser.verifyVerificationCode(code));
    }

    @Test
    @DisplayName("로그인 시 lastLoginAt이 업데이트 된다")
    void login() {
        Instant now = Instant.now();
        Instant currentValue = this.joinedUser.getLastLoginAt();
        joinedUser.login();

        Assertions.assertAll(
                () -> Assertions.assertNotEquals(currentValue, joinedUser.getLastLoginAt()),
                () -> Assertions.assertNotNull(joinedUser.getLastLoginAt()),
                () -> Assertions.assertTrue(joinedUser.getLastLoginAt().isAfter(now))
        );
    }
}