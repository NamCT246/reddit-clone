package com.namct.reddit.auth.token.refreshToken;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    @NotBlank
    private String refreshToken;
    private String username;
}