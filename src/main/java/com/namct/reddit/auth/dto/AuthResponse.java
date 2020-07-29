package com.namct.reddit.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Request to server will return a jwt token
 */

@Data
@AllArgsConstructor
public class AuthResponse {
    private String authenticationToken;
    private String username;
}