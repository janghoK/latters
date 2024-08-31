package com.jangho.latters.common.exception;

import com.jangho.latters.common.model.enums.ResponseCode;
import lombok.Getter;
import org.apache.coyote.Response;

@Getter
public class CustomException extends RuntimeException {
    private final ResponseCode responseCode;
    private final String message;

    public CustomException(ResponseCode responseCode) {
        this.responseCode = responseCode;
        this.message = responseCode.getMessage();
    }

    public CustomException(ResponseCode responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }
}
