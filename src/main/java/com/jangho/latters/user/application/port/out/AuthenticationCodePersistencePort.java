package com.jangho.latters.user.application.port.out;

import com.jangho.latters.user.domain.AuthenticationCode;

import java.util.Optional;

public interface AuthenticationCodePersistencePort {
    Optional<AuthenticationCode> findByEmail(String email);
    void create(AuthenticationCode authenticationCode);
    void delete(Long id);
}
