package com.jangho.latters.user.domain;

import lombok.Getter;

public record AuthenticationCode(Long id, Email email, VerificationCode verificationCode) {
    public static AuthenticationCode generate(String email) {
        return new AuthenticationCode(null, Email.from(email), VerificationCode.generate());
    }

    public void verifyVerificationCode(String code) {
        this.verificationCode.validate(code);
    }
}
