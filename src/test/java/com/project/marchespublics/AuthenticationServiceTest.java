package com.project.marchespublics;

import com.project.marchespublics.dto.authDto.LoginDto;
import com.project.marchespublics.dto.authDto.RegisterDto;
import com.project.marchespublics.dto.authDto.VerifyUserDto;
import com.project.marchespublics.enums.UserRole;
import com.project.marchespublics.mapper.UserMapper;
import com.project.marchespublics.model.User;
import com.project.marchespublics.repository.UserRepository;
import com.project.marchespublics.service.implementation.auth.AuthenticationService;
import com.project.marchespublics.service.implementation.auth.EmailService;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private EmailService emailService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthenticationService authenticationService;

    private RegisterDto registerDto;
    private LoginDto loginDto;
    private User user;

    @BeforeEach
    void setUp() {
        // Setup test data
        registerDto = new RegisterDto();
        registerDto.setUsername("testuser");
        registerDto.setEmail("test@example.com");
        registerDto.setPassword("password123");
        registerDto.setRole("USER");

        loginDto = new LoginDto();
        loginDto.setEmail("test@example.com");
        loginDto.setPassword("password123");

        user = new User("testuser", "test@example.com", "encoded_password");
        user.setVerificationCode("123456");
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        user.setRole(UserRole.USER);
    }

    @Test
    void testSignup_Success() throws MessagingException {
        // Arrange
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(user);
        doNothing().when(emailService).sendVerificationEmail(anyString(), anyString(), anyString());

        // Act
        User result = authenticationService.signup(registerDto);

        // Assert
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testAuthenticate_Success() {
        // Arrange
        loginDto.setEmail("chbnt.bilal@gmail.com");  // Make sure this matches!

        user.setEnabled(true);
        user.setEmail("chbnt.bilal@gmail.com");  // Also update the user email

        when(userRepository.findByEmail("chbnt.bilal@gmail.com")).thenReturn(Optional.of(user));
        Authentication mockAuth = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(mockAuth);

        // Act
        User result = authenticationService.authenticate(loginDto);

        // Assert
        assertNotNull(result);
        assertEquals("chbnt.bilal@gmail.com", result.getEmail());
    }

    @Test
    void testVerifyUser_Success() {
        // Arrange
        VerifyUserDto verifyUserDto = new VerifyUserDto();
        verifyUserDto.setEmail("test@example.com");
        verifyUserDto.setVerificationCode("123456");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        authenticationService.verifyUser(verifyUserDto);

        // Assert
        verify(userRepository).save(any(User.class));
    }

}