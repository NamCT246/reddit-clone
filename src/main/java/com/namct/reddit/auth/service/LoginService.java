package com.namct.reddit.auth.service;

import com.namct.reddit.auth.dto.AuthResponse;
import com.namct.reddit.auth.dto.Login;
import com.namct.reddit.auth.token.jwt.JwtProvider;
import com.namct.reddit.auth.token.refreshToken.RefreshToken;
import com.namct.reddit.auth.token.refreshToken.RefreshTokenRequest;
import com.namct.reddit.auth.token.refreshToken.RefreshTokenService;
import com.namct.reddit.exceptions.BaseException;
import com.namct.reddit.users.UserModel;
import com.namct.reddit.users.UserRepository;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import static java.time.Instant.now;

import javax.validation.Valid;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private RefreshTokenService refreshTokenService;

    // TODO: add username type check so user can login with email or username
    public AuthResponse login(Login loginParams) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginParams.getUsername(), loginParams.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);

        return AuthResponse.builder()
            .authenticationToken(authenticationToken)
            .refreshToken(refreshTokenService.generateAndSaveToken().getToken())
            .expiredAt(now().plusMillis(jwtProvider.getTokenExpirationInMillis()))
            .username(loginParams.getUsername())
            .build();
    }

    public UserModel getCurrentLoggedInUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

	public AuthResponse validateRefreshToken(RefreshTokenRequest token) {
       refreshTokenService.validateToken(token.getRefreshToken());

        return AuthResponse.builder()
            .authenticationToken(jwtProvider.generateTokenFromUsername(token.getUsername()))
            .refreshToken(token.getRefreshToken())
            .expiredAt(now().plusMillis(jwtProvider.getTokenExpirationInMillis()))
            .username(token.getUsername())
            .build();
	}
}