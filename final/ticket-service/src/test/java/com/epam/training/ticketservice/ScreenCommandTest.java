package com.epam.training.ticketservice;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.ui.command.ScreenCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class ScreenCommandTest {
    @Mock
    private ScreeningService screeningService;

    @Mock
    private UserService userService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private ScreenCommand screenCommand;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createScreening_ShouldReturnSuccessMessage_WhenScreeningCreationIsSuccessful() throws ParseException, NoSuchFieldException, IllegalAccessException {
        // Arrange
        Field movieRepositoryField = ScreenCommand.class.getDeclaredField("movieRepository");
        movieRepositoryField.setAccessible(true);
        movieRepositoryField.set(screenCommand, movieRepository);

        Field roomRepositoryField = ScreenCommand.class.getDeclaredField("roomRepository");
        roomRepositoryField.setAccessible(true);
        roomRepositoryField.set(screenCommand, roomRepository);

        Movie movie = new Movie("TestMovie", "TestCategory", 120);
        Room room = new Room("TestRoom", 10, 10);
        LocalDateTime screeningTime = LocalDateTime.parse("2023-01-01 14:00", formatter);

        when(screeningService.registerScreen(any())).thenReturn("Screening created successfully");
        when(movieRepository.findByTitle("TestMovie")).thenReturn(Optional.of(movie));
        when(roomRepository.findByName("TestRoom")).thenReturn(Optional.of(room));

        // Act
        String result = screenCommand.createScreening("TestMovie", "TestRoom", "2023-01-01 14:00");

        // Assert
        assertEquals("Screening created successfully", result);
    }

    @Test
    void listScreening_ShouldReturnNoScreeningsMessage_WhenNoScreensExist() {
        // Arrange
        when(screeningService.listScreens()).thenReturn(Collections.emptyList());

        // Act
        String result = screenCommand.listScreening();

        // Assert
        assertEquals("There are no screenings", result);
    }

    @Test
    void deleteScreening_ShouldReturnSuccessMessage_WhenScreeningDeletionIsSuccessful() {
        // Arrange
        doNothing().when(screeningService).removeScreen(any(), any(), any());

        // Act
        String result = screenCommand.deleteScreening("AnotherMovie", "AnotherRoom", "2023-01-01 14:00");

        // Assert
        assertEquals("Screening deleted successfully", result);
    }
    /*
    @Test
    void isAvailable_ShouldReturnAvailable_WhenUserIsAdmin() throws Exception {
        // Arrange
        when(userService.describe()).thenReturn(Optional.of(new UserDto("admin", "Admin", User.User_right.ADMIN)));

        // Act
        Availability availability = invokePrivateMethod(screenCommand, "isAvailable");

        // Assert
        assertEquals(Availability.available(), availability);
    }

    @Test
    void isAvailable_ShouldReturnUnavailable_WhenUserIsNotAdmin() throws Exception {
        // Arrange
        when(userService.describe()).thenReturn(Optional.of(new UserDto("user", "User", User.User_right.USER)));

        // Act
        Availability availability = invokePrivateMethod(screenCommand, "isAvailable");

        // Assert
        assertEquals(Availability.unavailable("You are not an admin!"), availability);
    }

    @Test
    void isAvailable_ShouldReturnUnavailable_WhenUserIsNotLoggedIn() throws Exception {
        // Arrange
        when(userService.describe()).thenReturn(Optional.empty());

        // Act
        Availability availability = invokePrivateMethod(screenCommand, "isAvailable");

        // Assert
        assertEquals(Availability.unavailable("You are not logged in!"), availability);
    }

    // Helper method to invoke private methods using reflection
    private <T> T invokePrivateMethod(Object object, String methodName) throws Exception {
        Method method = object.getClass().getDeclaredMethod(methodName);
        method.setAccessible(true);
        return (T) method.invoke(object);
    }
    */
}

