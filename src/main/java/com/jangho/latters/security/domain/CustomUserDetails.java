package com.jangho.latters.security.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private final Long id;
    private String username;
    private String password;
    private String roleType;
    private List<GrantedAuthority> authorities;
    private Instant activatedAt;
    private String name;

    public CustomUserDetails(Long id) {
        this.id = id;
    }

    // for login
    public CustomUserDetails(Long id, String username, String name, String password, String roleType, Instant activatedAt) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.password = password;
        this.roleType = roleType;
        this.authorities = new ArrayList<>(AuthorityUtils.createAuthorityList(this.roleType));
        this.activatedAt = activatedAt;
    }

    public CustomUserDetails(Long id, String username, String name, String roleType, Instant activatedAt) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.roleType = roleType;
        this.authorities = new ArrayList<>(AuthorityUtils.createAuthorityList(this.roleType));
        this.activatedAt = activatedAt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public List<String> getUserAuthorities() {
        return this.authorities.stream().map(Object::toString).toList();
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public String getName() {
        return this.name;
    }

    public String getRoleType() {
        return this.roleType;
    }

    public Instant getActivatedAt() {
        return this.activatedAt;
    }

    @Override
    public boolean isAccountNonExpired() {
        return activatedAt != null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return activatedAt != null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return activatedAt != null;
    }

    @Override
    public boolean isEnabled() {
        return activatedAt != null;
    }
}
