package com.jangho.latters.user.domain;

import lombok.Getter;

public record AuthenticationCode(Email email, VerificationCode verificationCode) {
    public static AuthenticationCode generate(Email email) {
        return new AuthenticationCode(email, VerificationCode.generate());
    }

    public void verifyVerificationCode(String code) {
        this.verificationCode.validate(code);
    }
}
