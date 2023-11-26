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

    @ShellMethod(key = "add movie", value = "Add a new movie")
    public String createMovie(String title, String category, Integer length) {
        try {
            movieService.createMovie(title, category, length);
            return "Movie added successfully!";
        } catch (Exception e) {
            return "Failed to add movie!";
        }
    }

    @ShellMethod(key = "list movies", value = "List all movies")
    public List<MovieDTO> listMovies() {
        return movieService.listMovies();
    }
}

