package com.jangho.latters.security.domain.jwt;

import com.jangho.latters.common.model.enums.ResponseCode;
import com.jangho.latters.security.domain.exception.SecurityException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class TokenBundle {
    private String accessToken;
    private Instant accessTokenExpiredAt;
    private String refreshToken;
    private Instant refreshTokenExpiredAt;

    public TokenBundle(String accessToken, String refreshToken) {
        if(!StringUtils.hasText(accessToken) || !StringUtils.hasText(refreshToken)) {
            throw new SecurityException(ResponseCode.UNAUTHORIZED, "accessToken 혹은 refreshToken이 null 입니다.");
        }
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public TokenBundle(TokenData accessToken, TokenData refreshToken) {
        this.accessToken = accessToken.token();
        this.accessTokenExpiredAt = accessToken.expiredAt();
        this.refreshToken = refreshToken.token();
        this.refreshTokenExpiredAt = refreshToken.expiredAt();

    }

    public void updateTokens(TokenData accessToken, TokenData refreshToken) {
        this.accessToken = accessToken.token();
        this.accessTokenExpiredAt = accessToken.expiredAt();
        this.refreshToken = refreshToken.token();
        this.refreshTokenExpiredAt = refreshToken.expiredAt();
    }
}