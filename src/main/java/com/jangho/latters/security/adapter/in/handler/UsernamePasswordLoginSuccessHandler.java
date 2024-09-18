package com.jangho.latters.security.adapter.in.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jangho.latters.security.adapter.in.dto.JwtDto;
import com.jangho.latters.security.application.port.in.JwtUseCase;
import com.jangho.latters.security.application.port.in.UserDetailsUseCase;
import com.jangho.latters.security.domain.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UsernamePasswordLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UserDetailsUseCase userDetailsUseCase;
    private final JwtUseCase jwtUseCase;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        userDetailsUseCase.login(authentication.getName());

        JwtDto jwt = JwtDto.from(jwtUseCase.generateTokenBundle(userDetails));

        ResponseEntity<JwtDto> res = ResponseEntity.ok(jwt);
        String result = new ObjectMapper().writeValueAsString(res);
        response.getWriter().write(result);
    }
}
