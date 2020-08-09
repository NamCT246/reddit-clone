package com.namct.reddit.auth.token.refreshToken;

import java.time.Instant;
import java.util.UUID;

import com.namct.reddit.exceptions.BaseException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RefreshTokenService  {

    private RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public RefreshToken generateAndSaveToken () {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedAt(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    public void validateToken(String token) {
        refreshTokenRepository.findByToken(token).orElseThrow(() -> new BaseException("Invalid refresh token"));
    }

    public void deleteToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}