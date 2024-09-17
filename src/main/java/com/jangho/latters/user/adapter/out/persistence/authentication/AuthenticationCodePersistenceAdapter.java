package com.jangho.latters.user.adapter.out.persistence.authentication;

import com.jangho.latters.common.annotation.Adapter;
import com.jangho.latters.user.application.port.out.AuthenticationCodePersistencePort;
import com.jangho.latters.user.domain.AuthenticationCode;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Adapter
@RequiredArgsConstructor
public class AuthenticationCodePersistenceAdapter implements AuthenticationCodePersistencePort {

    private final AuthenticationCodeRepository authenticationCodeRepository;

    @Override
    public Optional<AuthenticationCode> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void create(AuthenticationCode authenticationCode) {

    }

    @Override
    public void delete(Long id) {

    }
}
