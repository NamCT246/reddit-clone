package com.namct.reddit.auth.controller;

import lombok.AllArgsConstructor;

import javax.validation.Valid;

import com.namct.reddit.auth.dto.AuthResponse;
import com.namct.reddit.auth.dto.Login;
import com.namct.reddit.auth.dto.Register;
import com.namct.reddit.auth.service.RegisterService;
import com.namct.reddit.auth.token.refreshToken.RefreshTokenRequest;
import com.namct.reddit.auth.token.refreshToken.RefreshTokenService;
import com.namct.reddit.auth.service.LoginService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private RegisterService registerService;
    private LoginService loginService;
    private RefreshTokenService refreshTokenService;

    /**
     * TODO: Currently no handling for existed user. 
     * Might create a layer between service and controller?
    */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Register register) {
        registerService.signup(register);
        return new ResponseEntity<>("Verification email sent.", HttpStatus.OK);
    }

    @GetMapping("/verify_account/{token}")
    public ResponseEntity<String> verify(@PathVariable String token){
        registerService.verifyAccount(token);
        return new ResponseEntity<>("Account verified.", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody Login loginData) {
        return loginService.login(loginData);
    }

    // Whenever JWT expired, request to check the refresh token is valid, then return the new JWT and a new refresh token
    @PostMapping("/refreshtoken")
    public AuthResponse refreshTokenResponse(@Valid @RequestBody RefreshTokenRequest token) {
        return loginService.validateRefreshToken(token);
    }

    // Whenever user logged out, refresh token should be deleted
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest token) {
        refreshTokenService.deleteToken(token.getRefreshToken());

        return new ResponseEntity<>("Refresh token deleted.", HttpStatus.OK);
    }
}