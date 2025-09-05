package com.example.demo.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

/**
 * Configuration class for Spring Security and application-specific beans.
 * Defines beans for user details service, authentication provider, authentication manager, and password encoder.
 */
@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final UserRepository repository;

    /**
     * Creates a UserDetailsService that loads user details by email from the UserRepository.
     *
     * @return A UserDetailsService that retrieves user details for authentication.
     * @throws UsernameNotFoundException if the user with the specified email is not found.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = repository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .build();
        };
    }

    /**
     * Configures a DaoAuthenticationProvider with the UserDetailsService and PasswordEncoder.
     *
     * @return An AuthenticationProvider for handling authentication.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Provides an AuthenticationManager for managing authentication processes.
     *
     * @param config The AuthenticationConfiguration to retrieve the AuthenticationManager.
     * @return The configured AuthenticationManager.
     * @throws Exception if an error occurs while retrieving the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Creates a BCryptPasswordEncoder for encoding and verifying passwords.
     *
     * @return A PasswordEncoder instance using BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}