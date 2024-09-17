package com.jangho.latters.user.adapter.in.web;

import com.jangho.latters.user.adapter.in.web.dto.UserResponseDto;
import com.jangho.latters.user.application.port.in.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserUseCase userUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUsers(@PathVariable Long id) {
        return ResponseEntity.ok(UserResponseDto
                .from(userUseCase.findById(id)));
    }

}
