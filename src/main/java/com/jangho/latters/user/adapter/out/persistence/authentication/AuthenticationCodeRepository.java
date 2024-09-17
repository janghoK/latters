package com.jangho.latters.user.adapter.out.persistence.authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationCodeRepository extends JpaRepository<AuthenticationCodeEntity, Long> {
}
