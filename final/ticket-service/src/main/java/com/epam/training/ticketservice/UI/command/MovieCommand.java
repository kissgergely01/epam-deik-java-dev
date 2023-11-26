package com.epam.training.ticketservice.UI.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDTO;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
@AllArgsConstructor
public class MovieCommand {
    private final MovieService movieService;

    @ShellMethod(key = "add movie", value = "Movie added")
    public String addMovie(String title, String genre, Integer duration) {
        try {
            movieService.addMovie(title, genre,duration);
            return "Create movie was successful!";
        } catch (Exception e) {
            return "Create movie failed!";
        }
    }
    @ShellMethod(key = "list movies", value = "Get movies")
    public List<MovieDTO> description(){
        return movieService.listMovies();

    }
}
