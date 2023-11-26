package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.screening.model.ScreeningDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningService {
    String registerScreen(ScreeningDTO screeningDto);

    boolean checkForOverlap(ScreeningDTO screeningDto);

    List<ScreeningDTO> listScreens();

    void removeScreen(String movieName, String roomName, LocalDateTime screeningDate);
}