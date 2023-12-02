package com.epam.training.ticketservice;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.screening.ScreeningServiceImpl;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import com.epam.training.ticketservice.core.screening.persistence.Screening;
import com.epam.training.ticketservice.core.screening.persistence.ScreeningRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        doNothing().when(screenRepository).deleteByTitleAndRoom_NameAndScreeningDate(
                any(Movie.class), anyString(), any(LocalDateTime.class));

        // Act
        assertDoesNotThrow(() -> screenService.removeScreen("Movie Title", "RoomName", LocalDateTime.now()));

        // Assert
        verify(movieRepository, times(1)).findByTitle(anyString());
        verify(screenRepository, times(1)).deleteByTitleAndRoom_NameAndScreeningDate(
                any(Movie.class), anyString(), any(LocalDateTime.class));
    }

    @Test
    void testRegisterScreen_Success() {
        // Arrange
        ScreeningDto screeningDto = createScreeningDto();
        when(screenRepository.save(any(Screening.class))).thenReturn(new Screening());

        // Act
        String result = screenService.registerScreen(screeningDto);

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("Screening"));
        verify(screenRepository, times(1)).save(any(Screening.class));
    }
    /*
    @Test
    void testRegisterScreen_Overlap() {
        // Arrange
        ScreeningDto screeningDto = createScreeningDto();
        when(screenRepository.findByScreeningDateGreaterThanEqualAndScreeningEndDateLessThanEqualAndRoom_Name(
                any(LocalDateTime.class), any(LocalDateTime.class), anyString()))
                .thenReturn(Optional.of(new Screening()));

        // Act
        String result = screenService.registerScreen(screeningDto);

        // Assert
        assertEquals("There is an overlapping screening", result);
        verify(screenRepository, times(0)).save(any(Screening.class));
    }*/
    /*
    @Test
    void testRegisterScreen_BreakPeriod() {
        // Arrange
        ScreeningDto screeningDto = createScreeningDto();
        when(screenRepository.findByScreeningEndDateGreaterThanEqualAndScreeningEndDateLessThanEqualAndRoom_Name(
                any(LocalDateTime.class), any(LocalDateTime.class), anyString()))
                .thenReturn(Optional.of(new Screening()));

        // Act
        String result = screenService.registerScreen(screeningDto);

        // Assert
        assertEquals("This would start in the break period after another screening in this room", result);
        verify(screenRepository, times(0)).save(any(Screening.class));
    }
    */
    /*
    @Test
    void testCheckForOverlap() {
        // Arrange
        ScreeningDto screeningDto = createScreeningDto();
        when(screenRepository.findByScreeningDateGreaterThanEqualAndScreeningEndDateLessThanEqualAndRoom_Name(
                any(LocalDateTime.class), any(LocalDateTime.class), anyString()))
                .thenReturn(Optional.of(new Screening()));

        // Act
        boolean result = screenService.checkForOverlap(screeningDto);

        // Assert
        assertTrue(result);
    }*/

    @Test
    void testCreateScreeningFromDto() {
        // Arrange
        ScreeningDto screeningDto = createScreeningDto();

        // Act
        Screening result = invokePrivateMethod(screenService, "createScreeningFromDto", ScreeningDto.class, screeningDto);

        // Assert
        assertNotNull(result);
        assertEquals(screeningDto.getRoom(), result.getRoom());
        assertEquals(screeningDto.getTitle(), result.getTitle());
        assertEquals(screeningDto.getScreeningDate(), result.getScreeningDate());
        assertEquals(screeningDto.getScreeningEndDate(), result.getScreeningEndDate());
    }

    private <T> T invokePrivateMethod(Object object, String methodName, Class<?> parameterType, Object argument) {
        try {
            Method method = object.getClass().getDeclaredMethod(methodName, parameterType);
            method.setAccessible(true);
            return (T) method.invoke(object, argument);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Error invoking private method", e);
        }
    }

    private ScreeningDto createScreeningDto() {
        return ScreeningDto.builder()
                .title(new Movie())
                .room(new Room())
                .screeningDate(LocalDateTime.now())
                .screeningEndDate(LocalDateTime.now().plusHours(2))
                .build();
    }
}
