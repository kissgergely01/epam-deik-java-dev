package com.epam.training.ticketservice;

import com.epam.training.ticketservice.core.Users.UserServiceImpl;
import com.epam.training.ticketservice.core.Users.model.UserDto;
import com.epam.training.ticketservice.core.Users.persistence.User;
import com.epam.training.ticketservice.core.Users.persistence.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        // Arrange
        String username = "john_doe";
        String password = "secure123";
        User user = new User(username, password, User.User_right.USER);

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.of(user));

        // Act
        Optional<UserDto> loggedInUser = userService.login(username, password);

        // Assert
        assertTrue(loggedInUser.isPresent());
        assertEquals(username, loggedInUser.get().getName());
        assertEquals(password, loggedInUser.get().getPassword());
        assertEquals(User.User_right.USER, loggedInUser.get().getUser_right());
    }

    @Test
    void testLoginInvalidCredentials() {
        // Arrange
        String username = "nonexistent_user";
        String password = "invalid_password";

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.empty());

        // Act
        Optional<UserDto> loggedInUser = userService.login(username, password);

        // Assert
        assertTrue(loggedInUser.isEmpty());
    }

    @Test
    void testAdminLogin() {
        // Arrange
        String username = "admin_user";
        String password = "admin123";
        User adminUser = new User(username, password, User.User_right.ADMIN);

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.of(adminUser));

        // Act
        Optional<UserDto> loggedInAdmin = userService.login(username, password);

        // Assert
        assertTrue(loggedInAdmin.isPresent());
        assertEquals(username, loggedInAdmin.get().getName());
        assertEquals(password, loggedInAdmin.get().getPassword());
        assertEquals(User.User_right.ADMIN, loggedInAdmin.get().getUser_right());
    }

    @Test
    void testLoginInvalidAdminCredentials() {
        // Arrange
        String username = "nonexistent_admin";
        String password = "invalid_admin";

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.empty());

        // Act
        Optional<UserDto> loggedInAdmin = userService.login(username, password);

        // Assert
        assertTrue(loggedInAdmin.isEmpty());
    }

    @Test
    void testLogout() {
        // Arrange
        String username = "logged_in_user";
        String password = "active_user";
        User user = new User(username, password, User.User_right.USER);

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.of(user));

        // Login first
        userService.login(username, password);

        // Act
        Optional<UserDto> loggedOutUser = userService.logout();

        // Assert
        assertTrue(loggedOutUser.isPresent());
        assertEquals(username, loggedOutUser.get().getName());
        assertEquals(password, loggedOutUser.get().getPassword());
        assertEquals(User.User_right.USER, loggedOutUser.get().getUser_right());

        // Check that the user is actually logged out
        Optional<UserDto> loggedInUser = userService.describe();
        assertTrue(loggedInUser.isEmpty());
    }

    @Test
    void testDescribe() {
        // Arrange
        String username = "admin_user";
        String password = "admin123";
        User adminUser = new User(username, password, User.User_right.ADMIN);

        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.of(adminUser));

        // Login first
        userService.login(username, password);

        // Act
        Optional<UserDto> loggedInAdmin = userService.describe();

        // Assert
        assertTrue(loggedInAdmin.isPresent());
        assertEquals(username, loggedInAdmin.get().getName());
        assertEquals(password, loggedInAdmin.get().getPassword());
        assertEquals(User.User_right.ADMIN, loggedInAdmin.get().getUser_right());
    }

    @Test
    void testRegisterUser() {
        // Arrange
        String username = "new_user";
        String password = "new_password";

        // Act
        userService.registerUser(username, password);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterAdmin() {
        // Arrange
        String username = "admin_user";
        String password = "admin_password";

        // Act
        userService.registerUser(username, password);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
    }
}