package com.example.demo.auth.controller;

import com.example.demo.auth.dto.request.CreateUserRequest;
import com.example.demo.auth.dto.request.LoginRequest;
import com.example.demo.auth.dto.response.RegisterUserResponse;
import com.example.demo.user.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    private UserService userService;
    private AuthController authController;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        authController = new AuthController(userService);
    }

    @Test
    void create_returnsCreatedUserResponse() {
        // Arrange
        CreateUserRequest request = new CreateUserRequest(
                "John Doe",
                "john@example.com",
                "password123",
                null
        );

        RegisterUserResponse response = new RegisterUserResponse(
                UUID.randomUUID(),
                "John Doe",
                "john@example.com",
                "token123",
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                true
        );

        when(userService.create(request)).thenReturn(response);

        // Act
        ResponseEntity<RegisterUserResponse> result = authController.create(request);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);

        verify(userService).create(request);
    }

    @Test
    void login_returnsLoginUserResponse() {
        // Arrange
        LoginRequest request = new LoginRequest(
                "john@example.com",
                "password123"
        );

        RegisterUserResponse response = new RegisterUserResponse(
                UUID.randomUUID(),
                "John Doe",
                "john@example.com",
                "token123",
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                true
        );

        when(userService.login(request)).thenReturn(response);

        // Act
        ResponseEntity<RegisterUserResponse> result = authController.login(request);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isEqualTo(response);

        verify(userService).login(request);
    }
}
