package com.jangho.latters.user.domain;

import com.jangho.latters.common.exception.CustomException;
import com.jangho.latters.common.model.enums.ResponseCode;
import lombok.Getter;

@Getter
public class Email implements Credential{

    private final String email;

    private Email(String email) {
        this.email = email;
    }

    public static Email from(String email) {
        return new Email(email);
    }

    @Override
    public void validate() {
        if (!isValidEmail(email)) {
            throw new CustomException(ResponseCode.FAIL, "이메일 형식이 올바르지 않습니다.");
        }
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }
}
