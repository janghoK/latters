package com.jangho.latters.user.adapter.out.persistence.user;

import com.jangho.latters.common.annotation.Adapter;
import com.jangho.latters.user.application.port.out.UserPersistencePort;
import com.jangho.latters.user.domain.User;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Adapter
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserRepository userRepository;

    @Override
    public void create(User user) {

    }

    @Override
    public void update(Long id, User user) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }
}
