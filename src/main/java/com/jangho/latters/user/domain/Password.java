package com.jangho.latters.user.domain;

import com.jangho.latters.common.exception.CustomException;
import com.jangho.latters.common.model.enums.ResponseCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static com.jangho.latters.common.model.enums.ResponseCode.INTERNAL_SERVER_ERROR;

@Getter
public class Password implements Credential {

    private String password;

    @Setter
    private PasswordEncoder passwordEncoder;

    private Password(String rawPassword, PasswordEncoder passwordEncoder) {
        this.password = rawPassword;
        this.passwordEncoder = passwordEncoder;
    }

    public static Password of(String password, PasswordEncoder passwordEncoder) {
        return new Password(password, passwordEncoder);
    }

    public boolean match(String rawPassword) {
        validatePasswordEncoderNotNull();
        return passwordEncoder.matches(rawPassword, password);
    }

    public void changePassword(String currentPassword, String newPassword) {
        if (!StringUtils.hasText(currentPassword)) {
            throw new CustomException(ResponseCode.FAIL, "기존 비밀번호를 입력해주세요.");
        } else if (!StringUtils.hasText(newPassword)) {
            throw new CustomException(ResponseCode.FAIL, "새 비밀번호를 입력해주세요.");
        } else if (!match(currentPassword)) {
            throw new CustomException(ResponseCode.FAIL, "비밀번호가 일치하지 않습니다.");
        }

        this.password = newPassword;
        this.validate();
    }

    @Override
    public void validate() {
        if (!isValidPassword(password)) {
            throw new CustomException(ResponseCode.FAIL, "비밀번호는 8자 이상이어야 합니다.");
        }

        encode();
    }

    private void encode() {
        validatePasswordEncoderNotNull();
        this.password = passwordEncoder.encode(password);
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    private void validatePasswordEncoderNotNull() {
        if (Objects.isNull(this.passwordEncoder)) {
            throw new CustomException(INTERNAL_SERVER_ERROR, "Password encoder is not set.");
        }
    }



}
