package com.jangho.latters.security.application.port.in;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserDetailsUseCase extends UserDetailsService {
    void login(String email);
}
