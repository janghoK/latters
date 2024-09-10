package com.jangho.latters.user.domain;

import ch.qos.logback.core.testUtil.RandomUtil;
import com.jangho.latters.common.exception.CustomException;
import com.jangho.latters.common.model.enums.ResponseCode;
import lombok.Getter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Getter
public class VerificationCode {
    private static final Random random = new Random();
    private final String verificationCode;
    private final Instant verificationCodeExpiresIn;

    private VerificationCode(String verificationCode, Instant verificationCodeExpiresIn) {
        this.verificationCode = verificationCode;
        this.verificationCodeExpiresIn = verificationCodeExpiresIn;
    }

    public static VerificationCode of(String verificationCode, Instant verificationCodeExpiresIn) {
        return new VerificationCode(verificationCode, verificationCodeExpiresIn);
    }

    public static VerificationCode generate() {

        return new VerificationCode(digitCode(), Instant.now().plus(1, ChronoUnit.DAYS));
    }

    private boolean isExpired() {
        return Instant.now().isAfter(verificationCodeExpiresIn);
    }

    private static String digitCode() {
        final String characters = "0123456789";
        char[] randomChars = new char[6];
        for (int i = 0; i < 6; i++) {
            randomChars[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(randomChars);
    }

    public void validate(String verificationCode) {
        if (isExpired()) {
            throw new CustomException(ResponseCode.FAIL, "인증 코드가 만료되었습니다.");
        } else if (!this.verificationCode.equals(verificationCode)) {
            throw new CustomException(ResponseCode.FAIL, "인증 코드가 일치하지 않습니다.");
        }
    }
}
