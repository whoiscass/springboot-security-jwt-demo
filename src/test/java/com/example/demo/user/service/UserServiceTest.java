package com.example.demo.user.service;

import com.example.demo.auth.dto.request.CreateUserRequest;
import com.example.demo.auth.dto.request.LoginRequest;
import com.example.demo.auth.dto.request.PhoneDto;
import com.example.demo.auth.dto.response.RegisterUserResponse;
import com.example.demo.config.security.JwtService;
import com.example.demo.user.entity.Phone;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtService = mock(JwtService.class);

        userService = new UserService(userRepository, passwordEncoder, jwtService);
    }

    @Test
    void createUser_success() {
        PhoneDto phoneDto = new PhoneDto("123456789", "01", "57");
        CreateUserRequest request = new CreateUserRequest(
                "John Doe",
                "john@example.com",
                "password123",
                List.of(phoneDto)
        );

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.password())).thenReturn("hashedPassword");
        when(jwtService.generateToken(any(User.class))).thenReturn("mockedToken");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepository.save(userCaptor.capture())).thenAnswer(invocation -> {
            User user = userCaptor.getValue();
            user.setId(UUID.randomUUID());
            return user;
        });


        RegisterUserResponse response = userService.createUser(request);


        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo(request.name());
        assertThat(response.email()).isEqualTo(request.email());
        assertThat(response.token()).isEqualTo("mockedToken");
        assertThat(response.isActive()).isTrue();

        User savedUser = userCaptor.getValue();
        assertThat(savedUser.getPassword()).isEqualTo("hashedPassword");
        assertThat(savedUser.getPhones()).hasSize(1);
        assertThat(savedUser.getPhones().get(0).getNumber()).isEqualTo("123456789");

        verify(userRepository).findByEmail(request.email());
        verify(passwordEncoder).encode(request.password());
        verify(jwtService).generateToken(any(User.class));
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_emailAlreadyRegistered_throwsException() {

        CreateUserRequest request = new CreateUserRequest(
                "John Doe",
                "john@example.com",
                "password123",
                List.of()
        );

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Email already registered");

        verify(userRepository).findByEmail(request.email());
        verifyNoMoreInteractions(userRepository, passwordEncoder, jwtService);
    }

    @Test
    void login_success() {

        LoginRequest request = new LoginRequest("john@example.com", "password123");

        User user = User.builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .email("john@example.com")
                .password("hashedPassword")
                .created(LocalDateTime.now().minusDays(1))
                .modified(LocalDateTime.now().minusDays(1))
                .lastLogin(LocalDateTime.now().minusDays(1))
                .isActive(true)
                .build();

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("newMockedToken");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RegisterUserResponse response = userService.login(request);

        assertThat(response).isNotNull();
        assertThat(response.email()).isEqualTo(request.email());
        assertThat(response.token()).isEqualTo("newMockedToken");
        assertThat(response.isActive()).isTrue();

        verify(userRepository).findByEmail(request.email());
        verify(jwtService).generateToken(user);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void login_userNotFound_throwsException() {
        LoginRequest request = new LoginRequest("john@example.com", "password123");

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.login(request))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("User not found");

        verify(userRepository).findByEmail(request.email());
        verifyNoMoreInteractions(jwtService, userRepository);
    }
}
