package com.epam.training.ticketservice;

import com.epam.training.ticketservice.UI.command.UserCommand;
import com.epam.training.ticketservice.core.Users.UserService;
import com.epam.training.ticketservice.core.Users.model.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/*class UserCommandTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserCommand userCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*void testLogout() {
        // Arrange
        when(userService.logout()).thenReturn("John");

        // Act
        String result = userCommand.logout();

        // Assert
        assertEquals("John is logged out!", result);
        verify(userService, times(1)).logout(); // Verify that the service method was called
    }

    @Test
    void testLogoutNotLoggedIn() {
        // Arrange
        when(userService.logout()).thenReturn(Optional.empty());

        // Act
        String result = userCommand.logout();

        // Assert
        assertEquals("You need to login first!", result);
    }

    @Test
    void testAdminLoginSuccess() {
        // Arrange
        when(userService.adminLogin("admin", "admin")).thenReturn(Optional.of(createUserDto("Admin")));

        // Act
        String result = userCommand.adminLogin("admin", "admin");

        // Assert
        assertEquals("Admin is successfully logged in!", result);
    }

    @Test
    void testAdminLoginFailure() {
        // Arrange
        when(userService.adminLogin("invalid", "invalid")).thenReturn(Optional.empty());

        // Act
        String result = userCommand.adminLogin("invalid", "invalid");

        // Assert
        assertEquals("Login failed due to incorrect credentials", result);
    }

    // Similar tests for other methods can be written


}*/