package com.jangho.latters.security.adapter.in.handler;

import com.jangho.latters.common.model.ErrorResponse;
import com.jangho.latters.common.model.enums.ResponseCode;
import com.jangho.latters.security.domain.exception.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class SecurityExceptionHandler {
    @ExceptionHandler(value = {BadCredentialsException.class})
    protected ResponseEntity<ErrorResponse> handleSecurityBadCredentialsException(BadCredentialsException bce) {
        log.error("handleSecurityException throw BadCredentialsException : {}", bce.getMessage());
        return ErrorResponse.toResponseEntity(ResponseCode.FAIL, bce.getMessage());
    }

    @ExceptionHandler(value = {SecurityException.class})
    protected ResponseEntity<ErrorResponse> handleSecurityException(SecurityException e) {
        log.error("handleSecurityException throw CustomSecurityException : {}", e.getMessage());

        return ErrorResponse.toResponseEntity(e.getResponseCode(), e.getMessage());
    }
}