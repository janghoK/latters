package com.jangho.latters.common.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS(HttpStatus.OK, "0000", "성공"),
    FAIL(HttpStatus.BAD_REQUEST, "0001", "실패"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "0002", "리소스를 찾을 수 없음"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "0003", "인증되지 않은 사용자"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "0004", "권한이 없음"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "0005", "서버 오류")

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
