package com.namct.reddit.auth.token.jwt;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Date;
import java.io.IOException;

import static java.time.Instant.now;

import javax.annotation.PostConstruct;

import com.namct.reddit.exceptions.BaseException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @Value("${jwt.expiration.time}")
    private Long tokenExpirationInMillis;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/reddit.jks");

            keyStore.load(resourceAsStream, "reddit".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new BaseException("Exception occurred while loading keystore from file");
        }
    }

    public String generateToken(Authentication authenticate) {
        User principal = (User) authenticate.getPrincipal();

        return Jwts.builder().setSubject(principal.getUsername()).setIssuedAt(Date.from(now()))
                .signWith(getPrivateKey()).setExpiration(Date.from(now().plusMillis(tokenExpirationInMillis)))
                .compact();
    }

    /**
     * As user context is not valid anymore, this function allows generate a new JWT
     * based on username
     * 
     * @param authenticate
     * @return
     */
    public String generateTokenFromUsername(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(Date.from(now())).signWith(getPrivateKey())
                .setExpiration(Date.from(now().plusMillis(tokenExpirationInMillis))).compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("reddit", "reddit".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new BaseException("Exception occurred while retrieving public key from keystore");
        }
    }

    public String getUsernameFromJwt(String token) {

        Claims claims = parser().setSigningKey(getPublicKey()).parseClaimsJws(token).getBody();

        return claims.getSubject();

    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("reddit").getPublicKey();
        } catch (KeyStoreException e) {
            throw new BaseException("Exception occurred while retrieving public key from keystore");
        }
    }

    public boolean validateToken(String jwt) {
        parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
        return true;
    }

    public Long getTokenExpirationInMillis() {
        return tokenExpirationInMillis;
    }
}