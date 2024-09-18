package com.jangho.latters.security.application.port.in;

import com.jangho.latters.security.domain.CustomUserDetails;
import com.jangho.latters.security.domain.jwt.TokenBundle;

public interface JwtUseCase {
    TokenBundle generateTokenBundle(CustomUserDetails bundle);
    TokenBundle refreshAccessToken(TokenBundle bundle);

    void validateToken(String token);
}
