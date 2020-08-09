package com.namct.reddit.auth.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for data returned from a login request
 * Request to server will return a jwt token
 */

@Data
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String authenticationToken;
    private String refreshToken;
    private Instant expiredAt;
    private String username;
}