package com.epam.training.ticketservice;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.ui.command.MovieCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MovieCommandTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieCommand movieCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void updateMovie_ShouldReturnSuccessMessage_WhenMovieUpdateIsSuccessful() {
        // Arrange
        when(movieService.updateMovie(any(), any(), any())).thenReturn(Optional.of(new MovieDto("Updated Movie", "Drama", 120)));

        // Act
        String result = movieCommand.updateMovie("Test Movie", "Action", 120);

        // Assert
        assertEquals("Movie update was successful: Updated Movie (Drama, 120 minutes)", result);
    }

    @Test
    void updateMovie_ShouldReturnFailureMessage_WhenMovieUpdateFails() {
        // Arrange
        when(movieService.updateMovie(any(), any(), any())).thenReturn(Optional.empty());

        // Act
        String result = movieCommand.updateMovie("Test Movie", "Action", 120);

        // Assert
        assertEquals("Movie update failed! Movie not found.", result);
    }

    @Test
    void deleteMovie_ShouldReturnSuccessMessage_WhenMovieDeletionIsSuccessful() {
        // Arrange
        when(movieService.deleteMovie(any())).thenReturn(Optional.of(new MovieDto("Deleted Movie", "Comedy", 100)));

        // Act
        String result = movieCommand.deleteMovie("Test Movie");

        // Assert
        assertEquals("Movie delete was successful: Deleted Movie (Comedy, 100 minutes)", result);
    }

    @Test
    void deleteMovie_ShouldReturnFailureMessage_WhenMovieDeletionFails() {
        // Arrange
        when(movieService.deleteMovie(any())).thenReturn(Optional.empty());

        // Act
        String result = movieCommand.deleteMovie("Test Movie");

        // Assert
        assertEquals("Movie delete failed! Movie not found.", result);
    }
}


