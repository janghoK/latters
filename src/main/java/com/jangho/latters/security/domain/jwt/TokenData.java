package com.jangho.latters.security.domain.jwt;

import java.time.Instant;

public record TokenData(
        String token,
        Instant expiredAt
) {
}
