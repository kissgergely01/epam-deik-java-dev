package com.epam.training.ticketservice;

import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.Users.UserService;
import com.epam.training.ticketservice.UI.command.ScreenCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.format.DateTimeFormatter;
import java.util.Collections;

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
}

