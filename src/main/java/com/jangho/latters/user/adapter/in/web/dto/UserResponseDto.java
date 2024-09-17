package com.jangho.latters.user.adapter.in.web.dto;

import com.jangho.latters.user.domain.User;

public record UserResponseDto(
        Long id,
        String email,
        String name
) {

    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getName()
        );
    }
}