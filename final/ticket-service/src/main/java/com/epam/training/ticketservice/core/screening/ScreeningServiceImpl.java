package com.epam.training.ticketservice.core.screening;

import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.screening.model.ScreeningDto;
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
    public String registerScreen(ScreeningDto screeningDTO) {
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
    private Screening createScreeningFromDto(ScreeningDto screeningDTO) {
        Screening screening = new Screening();
        screening.setRoom(screeningDTO.getRoom());
        screening.setTitle(screeningDTO.getTitle());
        screening.setScreeningDate(screeningDTO.getScreeningDate());
        screening.setScreeningEndDate(screeningDTO.getScreeningEndDate());
        return screening;
    }
    private boolean isBreakPeriod(ScreeningDto screeningDTO) {
        return screeningRepository
                .findBy_ScreeningEndDate_GreaterThanEqual_ScreeningEndDate_LessThanEqual_And_Room_Name(
                        screeningDTO.getScreeningDate().minusSeconds(10*60),
                        screeningDTO.getScreeningDate(),
                        screeningDTO.getRoom().getName())
                .isPresent();
    }

    public boolean checkForOverlap(ScreeningDto screeningDTO){
        return screeningRepository
                .findBy_ScreeningDate_GreaterThan_ScreeningEndDate_LessThanEqual_And_Room_Name(
                        screeningDTO.getScreeningDate(), screeningDTO.getScreeningEndDate(), screeningDTO.getRoom().getName())
                .isPresent()
                || screeningRepository
                .findBy_ScreeningDate_GreaterThanEqual_ScreeningDate_LessThanEqual_And_Room_Name(
                        screeningDTO.getScreeningDate(), screeningDTO.getScreeningEndDate(), screeningDTO.getRoom().getName())
                .isPresent()
                || screeningRepository
                .findBy_ScreeningEndDate_GreaterThanEqual_ScreeningEndDate_LessThanEqual_And_Room_Name(
                        screeningDTO.getScreeningDate(), screeningDTO.getScreeningEndDate(), screeningDTO.getRoom().getName())
                .isPresent()
                || screeningRepository
                .findBy_ScreeningDate_LessThanEqual_ScreeningEndDate_GreaterThanEqual_And_Room_Name(
                        screeningDTO.getScreeningDate(), screeningDTO.getScreeningEndDate(), screeningDTO.getRoom().getName())
                .isPresent();
    }

    @Override
    public List<ScreeningDto> listScreens() {
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

    public ScreeningDto convertToDto(Screening screen){
        return ScreeningDto.builder()
                .title(screen.getTitle())
                .room(screen.getRoom())
                .screeningDate(screen.getScreeningDate())
                .screeningEndDate(screen.getScreeningEndDate())
                .build();
    }
}
