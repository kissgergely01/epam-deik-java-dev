package com.epam.training.ticketservice.core.screening.model;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.room.persistence.Room;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

;import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
public class ScreeningDTO {

    private Room room;
    private Movie title;
    private LocalDateTime screeningDate;
    private LocalDateTime screeningEndDate;

}