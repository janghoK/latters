package com.jangho.latters.user.application.port.out;

import com.jangho.latters.user.domain.User;

import java.util.Optional;

public interface UserPersistencePort {
    User create(User user);
    User update(Long id, User user);
    void delete(Long id);

    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);

}
