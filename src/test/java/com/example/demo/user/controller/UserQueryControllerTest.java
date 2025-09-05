package com.example.demo.user.controller;

import com.example.demo.user.entity.User;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserQueryControllerTest {

    private UserService userService;
    private UserQueryController userQueryController;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userQueryController = new UserQueryController(userService);
    }

    @Test
    void getUser_success() {
        // Arrange
        UUID userId = UUID.randomUUID();
        User user = User.builder()
                .id(userId)
                .name("Jane Doe")
                .email("jane@example.com")
                .created(LocalDateTime.now().minusDays(1))
                .modified(LocalDateTime.now().minusHours(2))
                .lastLogin(LocalDateTime.now().minusMinutes(10))
                .isActive(true)
                .build();

        when(userService.getUserById(userId)).thenReturn(user);

        // Act
        ResponseEntity<User> response = userQueryController.getUser(userId);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(user);

        verify(userService).getUserById(userId);
    }

    @Test
    void getUser_userNotFound_throwsException() {
        // Arrange
        UUID userId = UUID.randomUUID();

        when(userService.getUserById(userId))
                .thenThrow(new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "User not found"));

        // Act & Assert
        assertThatThrownBy(() -> userQueryController.getUser(userId))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User not found");

        verify(userService).getUserById(userId);
    }
}
