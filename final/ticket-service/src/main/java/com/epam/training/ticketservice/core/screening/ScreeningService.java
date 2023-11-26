package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningService {
    String registerScreen(ScreeningDto screeningDto);

    boolean checkForOverlap(ScreeningDto screeningDto);

    List<ScreeningDto> listScreens();

    void removeScreen(String movieName, String roomName, LocalDateTime screeningDate);
}