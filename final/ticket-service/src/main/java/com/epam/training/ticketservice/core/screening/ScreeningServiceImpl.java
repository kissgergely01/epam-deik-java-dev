package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.screening.model.ScreeningDTO;
import com.epam.training.ticketservice.core.screening.persistence.Screening;
import com.epam.training.ticketservice.core.screening.persistence.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScreeningServiceImpl implements ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final MovieRepository movieRepository;

    @Override
    public String registerScreen(ScreeningDTO screeningDTO) {
        if (checkForOverlap(screeningDTO)) {
            return "There is an overlapping screening";
        }

        if (isBreakPeriod(screeningDTO)) {
            return "This would start in the break period after another screening in this room";
        }

        Screening screen = createScreeningFromDto(screeningDTO);
        screeningRepository.save(screen);

        return screen.toString();
    }
    private Screening createScreeningFromDto(ScreeningDTO screenDto) {
        Screening screening = new Screening();
        screening.setRoom(screenDto.getRoom());
        screening.setTitle(screenDto.getTitle());
        screening.setScreeningDate(screenDto.getScreeningDate());
        screening.setScreeningEndDate(screenDto.getScreeningEndDate());
        return screening;
    }
    private boolean isBreakPeriod(ScreeningDTO screenDto) {
        return screeningRepository
                .findByScreeningEndDateGreaterThanEqualAndScreeningEndDateLessThanEqualAndRoom_Name(
                        screenDto.getScreeningDate().minusSeconds(10*60),
                        screenDto.getScreeningDate(),
                        screenDto.getRoom().getName())
                .isPresent();
    }

    public boolean checkForOverlap(ScreeningDTO screenDto){
        return screeningRepository
                .findByScreeningDateGreaterThanEqualAndScreeningEndDateLessThanEqualAndRoom_Name(
                        screenDto.getScreeningDate(), screenDto.getScreeningEndDate(), screenDto.getRoom().getName())
                .isPresent()
                || screeningRepository
                .findByScreeningDateGreaterThanEqualAndScreeningDateLessThanEqualAndRoom_Name(
                        screenDto.getScreeningDate(), screenDto.getScreeningEndDate(), screenDto.getRoom().getName())
                .isPresent()
                || screeningRepository
                .findByScreeningEndDateGreaterThanEqualAndScreeningEndDateLessThanEqualAndRoom_Name(
                        screenDto.getScreeningDate(), screenDto.getScreeningEndDate(), screenDto.getRoom().getName())
                .isPresent()
                || screeningRepository
                .findByScreeningDateLessThanEqualAndScreeningEndDateGreaterThanEqualAndRoom_Name(
                        screenDto.getScreeningDate(), screenDto.getScreeningEndDate(), screenDto.getRoom().getName())
                .isPresent();
    }

    @Override
    public List<ScreeningDTO> listScreens() {
        return screeningRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void removeScreen(String movieName, String roomName, LocalDateTime screenDate) {

        Movie movie = movieRepository.findByTitle(movieName)
                .orElseThrow(() -> new RuntimeException("Movie not found")); // Handle this exception appropriately

        screeningRepository.deleteByMovieAndRoom_NameAndDate(movie, roomName, screenDate);
    }


    public ScreeningDTO convertToDto(Screening screen){
        return ScreeningDTO.builder()
                .title(screen.getTitle())
                .room(screen.getRoom())
                .screeningDate(screen.getScreeningDate())
                .screeningEndDate(screen.getScreeningEndDate())
                .build();
    }
}
