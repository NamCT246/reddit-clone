package com.namct.reddit.auth.service;

import com.namct.reddit.auth.dto.AuthResponse;
import com.namct.reddit.auth.dto.Login;
import com.namct.reddit.auth.token.jwt.JwtProvider;
import com.namct.reddit.users.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    // TODO: add username type check so user can login with email or username
    public AuthResponse login(Login loginParams) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginParams.getUsername(), loginParams.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);

        return new AuthResponse(authenticationToken, loginParams.getUsername());
    }
}