package com.epam.training.ticketservice.UI.command;

import com.epam.training.ticketservice.core.Users.UserService;
import com.epam.training.ticketservice.core.Users.model.UserDTO;
import com.epam.training.ticketservice.core.Users.persistence.User;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import com.epam.training.ticketservice.core.screening.ScreeningService;
import com.epam.training.ticketservice.core.screening.model.ScreeningDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ScreenCommand {
    private final ScreeningService screenService;
    private final UserService userService;
    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private LocalDateTime date;

    //@ShellMethodAvailability("isAvailable")
    @ShellMethod(key = "create screening", value = " creating a screening in a cinema room")
    public String createScreening(String movieName, String roomName, String screeningTime) throws ParseException {
        date = LocalDateTime.parse(screeningTime, format);
        ScreeningDTO screeningDto = ScreeningDTO.builder()
                .title(movieRepository.findByTitle(movieName).get())
                .room(roomRepository.findByName(roomName).get())
                .screeningDate(date)
                .screeningEndDate(date.plusSeconds(movieRepository.findByTitle(movieName).get().getLength()* 60L))
                .build();
        return screenService.registerScreen(screeningDto);
    }

    @ShellMethod(key = "list screenings", value = "Shows all the existing screening")
    public String listScreening() {
        if (screenService.listScreens().isEmpty()) {
            return "There are no screenings";
        }

        return screenService.listScreens()
                .stream()
                .map(screen -> String.format("%s (%s, %d minutes), screened in room %s, at %s",
                        screen.getTitle().getTitle(),
                        screen.getTitle().getCategory(),
                        screen.getTitle().getLength(),
                        screen.getRoom().getName(),
                        screen.getScreeningDate().format(format)))
                .collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = "delete screening", value = "delete specific screening")
    public String deleteScreening(String movieName, String roomName, String screeningDate) {
        try {
            date = LocalDateTime.parse(screeningDate, format);
            screenService.removeScreen(movieName, roomName, date);
            return "Screening deleted successfully";
        } catch (Exception e) {
            return "Error deleting screening: " + e.getMessage();
        }
    }



    private Availability isAvailable(){
        Optional<UserDTO> user = userService.describe();
        return user.isPresent() &&  user.get().getUser_right() != User.User_right.ADMIN
                ? Availability.available()
                : Availability.unavailable("You are not an admin!");
    }
}
