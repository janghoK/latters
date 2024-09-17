package com.jangho.latters.common.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.security.encrypt")
public record SpringSecurityProperties (
        String aesPassword,
        String aesSalt
) {
}
