package com.epam.training.ticketservice;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.screening.ScreeningServiceImpl;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.Screening;
import com.epam.training.ticketservice.core.screening.persistence.ScreeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ScreeningTest {

    @Mock
    private ScreeningRepository screenRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private ScreeningServiceImpl screenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testListScreens() {
        // Arrange
        when(screenRepository.findAll()).thenReturn(Collections.singletonList(new Screening()));

        // Act
        List<ScreeningDto> result = screenService.listScreens();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(screenRepository, times(1)).findAll();
    }

    @Test
    void testRemoveScreen() {
        // Arrange
        Movie movie = new Movie();
        when(movieRepository.findByTitle(anyString())).thenReturn(Optional.of(movie));
        doNothing().when(screenRepository).deleteByMovieAndRoom_NameAndDate(
                any(Movie.class), anyString(), any(LocalDateTime.class));

        // Act
        assertDoesNotThrow(() -> screenService.removeScreen("Movie Title", "RoomName", LocalDateTime.now()));

        // Assert
        verify(movieRepository, times(1)).findByTitle(anyString());
        verify(screenRepository, times(1)).deleteByMovieAndRoom_NameAndDate(
                any(Movie.class), anyString(), any(LocalDateTime.class));
    }
}