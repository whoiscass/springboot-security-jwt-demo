package com.example.demo.user.controller;

import com.example.demo.auth.dto.request.UpdateUserRequest;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserCommandControllerTest {

    private UserService userService;
    private UserCommandController userCommandController;

    @BeforeEach
    public void setUp() {
        userService = mock(UserService.class);
        userCommandController = new UserCommandController(userService);
    }

    @Test
    public void testUpdateUser_Success() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UpdateUserRequest request = new UpdateUserRequest(
                "John Doe",
                "john.doe@example.com",
                "newSecurePassword123",
                Collections.emptyList(),
                LocalDateTime.now(),
                false
        );

        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setName(request.name());
        updatedUser.setEmail(request.email());
        updatedUser.setPassword(request.password());
        updatedUser.setCreated(LocalDateTime.now().minusDays(1));
        updatedUser.setModified(LocalDateTime.now());
        updatedUser.setLastLogin(LocalDateTime.now().minusHours(1));
        updatedUser.setActive(true);

        when(userService.update(request)).thenReturn(updatedUser);

        // Act
        ResponseEntity<User> response = userCommandController.update(userId, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody()).isNotNull();
        verify(userService, times(1)).update(request);
    }
}
