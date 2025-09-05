package com.example.demo.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * SecurityConfigProperties holds configuration properties related to JWT security.
 *
 * These properties are loaded from the application's configuration file (e.g., application.yml or application.properties)
 * using the prefix "spring.application.security.jwt".
 *
 * Example configuration:
 * spring.application.security.jwt.secret=your_base64_secret_key
 * spring.application.security.jwt.expiration=86400000
 *
 * @param secret the base64-encoded secret key used to sign JWT tokens
 * @param expiration the token expiration time in milliseconds
 */
@ConfigurationProperties(prefix = "spring.application.security.jwt")
public record SecurityConfigProperties(
        String secret,
        Long expiration
) {}
