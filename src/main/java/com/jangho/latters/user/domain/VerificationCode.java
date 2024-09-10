package com.jangho.latters.user.domain;

import com.jangho.latters.common.exception.CustomException;
import com.jangho.latters.common.model.enums.ResponseCode;
import lombok.Getter;

import java.time.Instant;

@Getter
public class VerificationCode {
    private final String verificationCode;
    private final Instant verificationCodeExpiresIn;

    private VerificationCode(String verificationCode, Instant verificationCodeExpiresIn) {
        this.verificationCode = verificationCode;
        this.verificationCodeExpiresIn = verificationCodeExpiresIn;
    }

    public static VerificationCode of(String verificationCode, Instant verificationCodeExpiresIn) {
        return new VerificationCode(verificationCode, verificationCodeExpiresIn);
    }

    private boolean isExpired() {
        return Instant.now().isAfter(verificationCodeExpiresIn);
    }

    protected void validate(String verificationCode) {
        if (isExpired()) {
            throw new CustomException(ResponseCode.FAIL, "인증 코드가 만료되었습니다.");
        } else if (!this.verificationCode.equals(verificationCode)) {
            throw new CustomException(ResponseCode.FAIL, "인증 코드가 일치하지 않습니다.");
        }
    }
}
