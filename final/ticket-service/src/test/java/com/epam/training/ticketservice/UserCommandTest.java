package com.epam.training.ticketservice;

import com.epam.training.ticketservice.core.Users.UserService;
import com.epam.training.ticketservice.core.Users.model.UserDTO;
import com.epam.training.ticketservice.UI.command.UserCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class UserCommandTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserCommand userCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void adminLogin_ShouldIndicateIncorrectCredentials_WhenAdminLoginFails() {
        // Arrange
        when(userService.adminLogin(any(), any())).thenReturn(Optional.empty());

        // Act
        String result = userCommand.adminLogin("admin", "wrongAdminPassword");

        // Assert
        assertEquals("Login attempt failed due to incorrect credentials", result);
    }

    @Test
    void login_ShouldNotifyUserOfIncorrectPassword_WhenUserLoginFails() {
        // Arrange
        when(userService.login(any(), any())).thenReturn(Optional.empty());

        // Act
        String result = userCommand.login("testUser", "incorrectUserPassword");

        // Assert
        assertEquals("Your login attempt failed. Check your username and password.", result);
    }

    @Test
    void description_ShouldRemindUserOfNotBeingLoggedIn_WhenUserIsNotLoggedIn() {
        // Arrange
        when(userService.describe()).thenReturn(Optional.empty());

        // Act
        String result = userCommand.description();

        // Assert
        assertEquals("You are currently not signed in. Sign in to access more features.", result);
    }

    @Test
    void registerUser_ShouldAcknowledgeRegistrationFailure_WhenUserRegistrationFails() {
        // Arrange
        // Simulating a failure by throwing an exception
        doThrow(new RuntimeException()).when(userService).registerUser(any(), any());

        // Act
        String result = userCommand.registerUser("newTestUser", "newTestPassword");

        // Assert
        assertEquals("Sorry, registration was unsuccessful. Please try again later.", result);
    }

}
