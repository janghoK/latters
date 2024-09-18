package com.jangho.latters.security.domain.exception;

import com.jangho.latters.common.model.enums.ResponseCode;
import lombok.Getter;

@Getter
public class SecurityException extends RuntimeException {
    private final ResponseCode responseCode;
    private final String message;

    public SecurityException(ResponseCode responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }
}
