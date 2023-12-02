package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDto;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import java.util.List;
import java.util.Optional;


@ShellComponent
@AllArgsConstructor
public class MovieCommand {
    private final MovieService movieService;

    @ShellMethod(key = "create movie", value = "Create a  Movie")
    public String createMovie(String title, String category, Integer duration) {
        try {
            movieService.createMovie(title, category, duration);
            return "Movie creation was successful!";
        } catch (Exception e) {
            return "Movie creation failed!";
        }
    }

    @ShellMethod(key = "list movies", value = "List all movies")
    public String listMovies() {
        List<MovieDto> movies = movieService.listMovies();
        if (movies.isEmpty()) {
            return "There are no movies at the moment";
        } else {
            StringBuilder result = new StringBuilder();
            for (MovieDto movie : movies) {
                result.append(String.format("%s (%s, %d minutes)\n",
                        movie.getTitle(), movie.getCategory(), movie.getLength()));
            }
            return result.toString();
        }
    }
    @ShellMethod(key = "update movie", value = "Update a movie")
    public String updateMovie(String title, String category, Integer duration) {
        Optional<MovieDto> updatedMovie = movieService.updateMovie(title, category, duration);
        return updatedMovie.map(movie -> "Movie update was successful: " + formatMovie(movie))
                .orElse("Movie update failed! Movie not found.");
    }

    @ShellMethod(key = "delete movie", value = "Delete a movie")
    public String deleteMovie(String title) {
        Optional<MovieDto> deletedMovie = movieService.deleteMovie(title);
        return deletedMovie.map(movie -> "Movie delete was successful: " + formatMovie(movie))
                .orElse("Movie delete failed! Movie not found.");
    }

    private String formatMovie(MovieDto movie) {
        return String.format("%s (%s, %d minutes)", movie.getTitle(), movie.getCategory(), movie.getLength());
    }
}

