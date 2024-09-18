package com.jangho.latters.common.model;

import com.jangho.latters.common.model.enums.ResponseCode;
import org.springframework.http.ResponseEntity;

public record ErrorResponse(
        String code,
        String message,
        String error,
        String traceId
) {
    private static ErrorResponse of(ResponseCode responseCode, String message) {
        // @TODO: mdc traceId 가져오기
        return new ErrorResponse(responseCode.getCode(), message, responseCode.getHttpStatus().name(), null);
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ResponseCode responseCode, String message) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(ErrorResponse.of(responseCode, message));
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ResponseCode responseCode) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(ErrorResponse.of(responseCode, responseCode.getMessage()));
    }
}
