package com.jangho.latters.user.application.service;

import com.jangho.latters.user.application.port.out.AuthenticationCodePersistencePort;
import com.jangho.latters.user.application.port.out.UserPersistencePort;
import com.jangho.latters.user.domain.AuthenticationCode;
import com.jangho.latters.user.domain.Email;
import com.jangho.latters.user.domain.Password;
import com.jangho.latters.user.domain.User;
import com.jangho.latters.user.domain.VerificationCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserPersistencePort userPersistencePort;

    @Mock
    private AuthenticationCodePersistencePort authenticationCodePersistencePort;

    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    private UserService userService;

    private User defaultGivenUser;

    @BeforeEach
    void beforeEach() {
        this.defaultGivenUser = User.builder()
                .id(1L)
                .email(Email.from("test@example.com"))
                .password(Password.of("password", passwordEncoder))
                .name("John Doe")
                .build();

        defaultGivenUser.getPassword().validate();
    }

    @Test
    @DisplayName("사용자 ID로 사용자를 찾는다.")
    void findById() {
        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(defaultGivenUser));

        User foundUser = userService.findById(1L);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, foundUser.getId()),
                () -> Assertions.assertEquals("test@example.com", foundUser.getEmail()),
                () -> Assertions.assertEquals("John Doe", foundUser.getName())
        );
    }

    @Test
    @DisplayName("사용자 이메일로 사용자를 찾는다.")
    void findByEmail() {
        when(userPersistencePort.findByEmail("test@example.com")).thenReturn(Optional.of(defaultGivenUser));

        User foundUser = userService.findByEmail("test@example.com");

        Assertions.assertAll(
                () -> Assertions.assertEquals(1L, foundUser.getId()),
                () -> Assertions.assertEquals("test@example.com", foundUser.getEmail()),
                () -> Assertions.assertEquals("John Doe", foundUser.getName())
        );
    }

    @Test
    @DisplayName("사용자 이메일로 인증 코드를 전송한다.(회원가입용)")
    void sendAuthenticationCode() {
        AuthenticationCode givenCode = AuthenticationCode.generate("test@example.com");
        doNothing().when(authenticationCodePersistencePort).create(any());

        Assertions.assertDoesNotThrow(() -> userService.sendAuthenticationCode("test@example.com"));
    }

    @Test
    @DisplayName("사용자 이메일로 인증 코드를 전송한다.")
    void sendVerificationCode() {
        when(userPersistencePort.findByEmail("test@example.com")).thenReturn(Optional.of(defaultGivenUser));
        when(userPersistencePort.update(1L, defaultGivenUser)).thenReturn(null);

        userService.sendVerificationCode("test@example.com");

        Assertions.assertNotNull(defaultGivenUser.getVerificationCode());
    }

    @Test
    @DisplayName("이메일, 비밀번호, 이름, 인증코드를 입력하고 회원가입한다.")
    void join() {
        String givenEmail = "test@example.com";
        String givenRawPassword = "password";
        String givenName = "John Doe";
        String givenAuthenticationCode = "1234";
        VerificationCode givenVerificationCode = VerificationCode.of(givenAuthenticationCode, Instant.now().plus(1, ChronoUnit.DAYS));
        AuthenticationCode givenCode = new AuthenticationCode(1L, Email.from(givenAuthenticationCode), givenVerificationCode);

        when(authenticationCodePersistencePort.findByEmail(givenEmail)).thenReturn(Optional.of(givenCode));
        when(userPersistencePort.create(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());
        doNothing().when(authenticationCodePersistencePort).delete(1L);

        User createdUser = userService.join(givenEmail, givenRawPassword, givenName, givenAuthenticationCode);

        Assertions.assertAll(
                () -> Assertions.assertEquals(givenEmail, createdUser.getEmail()),
                () -> Assertions.assertEquals(givenName, createdUser.getName()),
                () -> Assertions.assertNotNull(createdUser.getActivatedAt())
        );
    }

    @Test
    @DisplayName("사용자 정보를 수정한다.")
    void updateProfile() {
        String givenName = "Foo bar";

        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(defaultGivenUser));
        when(userPersistencePort.update(1L, defaultGivenUser)).thenAnswer(AdditionalAnswers.returnsSecondArg());

        User updatedUser = userService.updateProfile(1L, givenName);

        Assertions.assertEquals(givenName, updatedUser.getName());
    }

    @Test
    void resetPassword() {
        String givenEmail = "test@example.com";
        String newPassword = "newPassword";
        String verificationCode = "1234";
        VerificationCode givenVerificationCode = VerificationCode.of(verificationCode, Instant.now().plus(1, ChronoUnit.DAYS));
        User givenUser = User.builder()
                .id(1L)
                .email(Email.from(givenEmail))
                .password(Password.of("password", passwordEncoder))
                .verificationCode(givenVerificationCode)
                .build();

        when(userPersistencePort.findByEmail(givenEmail)).thenReturn(Optional.of(givenUser));
        when(userPersistencePort.update(1L, givenUser)).thenAnswer(AdditionalAnswers.returnsSecondArg());

        User updatedUser = userService.resetPassword(givenEmail, newPassword, verificationCode);

        Assertions.assertTrue(updatedUser.getPassword().match(newPassword));
    }

    @Test
    void updatePassword() {
        String currentPassword = "password";
        String newPassword = "newPassword";

        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(defaultGivenUser));
        when(userPersistencePort.update(1L, defaultGivenUser)).thenAnswer(AdditionalAnswers.returnsSecondArg());

        User updatedUser = userService.updatePassword(1L, currentPassword, newPassword);

        Assertions.assertTrue(updatedUser.getPassword().match(newPassword));
    }

    @Test
    void withdrawal() {
        doNothing().when(userPersistencePort).delete(1L);

        Assertions.assertDoesNotThrow(() -> userService.withdrawal(1L));
    }
}