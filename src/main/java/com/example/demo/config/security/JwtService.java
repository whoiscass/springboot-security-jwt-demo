package com.example.demo.config.security;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import lombok.RequiredArgsConstructor;

import com.example.demo.user.entity.User;

/**
 * JwtService handles the generation and validation of JSON Web Tokens (JWT)
 * using a shared secret key and HS256 signing algorithm.
 */
@Component
@RequiredArgsConstructor
public class JwtService {

    private final SecurityConfigProperties securityConfigProperties;

    /**
     * Generates a signed JWT token for the given user.
     *
     * The token contains the user's email as the subject, the current time as the issued time,
     * and an expiration time based on configuration.
     *
     * @param user the user for whom the token is generated
     * @return a signed JWT as a String
     */
    public String generateToken(final User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + securityConfigProperties.expiration()))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extracts the claims from the given JWT token.
     *
     * This method parses the token and verifies its signature using the secret key.
     *
     * @param token the JWT token from which to extract claims
     * @return the Claims object containing token data such as subject and expiration
     */
    public Claims extractUsername(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retrieves the secret key used for signing and validating JWT tokens.
     *
     * The key is derived from a base64-encoded string provided in the application configuration.
     * The decoded key must be at least 256 bits (32 bytes) long to be valid for HS256.
     *
     * @return the secret key as a SecretKey instance
     * @throws IllegalArgumentException if the key is too short
     */
    public SecretKey getSecretKey() {
        byte[] decodedKey = Decoders.BASE64.decode(securityConfigProperties.secret());
        if (decodedKey.length < 32) {
            throw new IllegalArgumentException("Secret key must be at least 256 bits (32 bytes)");
        }
        return Keys.hmacShaKeyFor(decodedKey);
    }
}
