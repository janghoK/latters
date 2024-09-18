package com.jangho.latters.security.adapter.in.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jangho.latters.common.model.enums.ResponseCode;
import com.jangho.latters.security.adapter.in.dto.LoginDto;
import com.jangho.latters.security.domain.exception.SecurityException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Map;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, HandlerExceptionResolver handlerExceptionResolver) {
        super(authenticationManager);
        this.handlerExceptionResolver = handlerExceptionResolver;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/login", "POST"));
        setUsernameParameter("email");
        setPasswordParameter("password");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new SecurityException(ResponseCode.FORBIDDEN, "method not allowed");
        }

        try {
            LoginDto credentials = getCredentials(request);
            String email = credentials.getEmail();
            String password = credentials.getPassword();

            UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.unauthenticated(email, password);

            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
            return null;
        }
    }

    private LoginDto getCredentials(HttpServletRequest request) {
        try {
            LoginDto loginDto = new ObjectMapper().readValue(request.getInputStream(), LoginDto.class);

            loginDto.validate();
            return loginDto;
        } catch (IOException e) {
            throw new SecurityException(ResponseCode.FAIL, "invalid request body");
        }
    }
}
