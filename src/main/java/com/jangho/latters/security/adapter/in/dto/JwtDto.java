package com.jangho.latters.security.adapter.in.dto;

import com.jangho.latters.security.domain.jwt.TokenBundle;

import java.time.Instant;

public record JwtDto(
        String accessToken,
        Instant accessTokenExpiredAt,
        String refreshToken,
        Instant refreshTokenExpiredAt
) {
    public static JwtDto from(TokenBundle tokenBundle) {
        return new JwtDto(
                tokenBundle.getAccessToken(),
                tokenBundle.getAccessTokenExpiredAt(),
                tokenBundle.getRefreshToken(),
                tokenBundle.getRefreshTokenExpiredAt()
        );
    }
}
