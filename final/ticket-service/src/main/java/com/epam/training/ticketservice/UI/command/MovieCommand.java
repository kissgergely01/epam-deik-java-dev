package com.epam.training.ticketservice.UI.command;

import com.epam.training.ticketservice.core.movie.MovieService;
import com.epam.training.ticketservice.core.movie.model.MovieDTO;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class MovieCommand {
    private final MovieService movieService;

    @ShellMethod(key = "add movie", value = "Add a new movie")
    public String createMovie(String title, String category, Integer duration) {
        try {
            movieService.createMovie(title, category, duration);
            return "Movie successfully added!";
        } catch (Exception e) {
            return "Failed to add the movie!";
        }
    }

    @ShellMethod(key = "list movies", value = "List all movies")
    public String listMovies() {
        List<MovieDTO> movies = movieService.listMovies();

        if (movies.isEmpty()) {
            return "There are no movies at the moment";
        } else {
            StringBuilder result = new StringBuilder();
            for (MovieDTO movie : movies) {
                result.append(String.format("%s (%s, %d minutes)\n",
                        movie.getTitle(), movie.getCategory(), movie.getLength()));
            }
            return result.toString();
        }
    }

    @ShellMethod(key = "update movie", value = "Update an existing movie")
    public String updateMovie(String title, String category, Integer duration) {
        Optional<MovieDTO> updatedMovie = movieService.updateMovie(title, category, duration);
        return updatedMovie.map(movie -> "Movie update successful: " + formatMovie(movie))
                .orElse("Failed to update the movie! Movie not found.");
    }

    @ShellMethod(key = "delete movie", value = "Delete an existing movie")
    public String deleteMovie(String title) {
        Optional<MovieDTO> deletedMovie = movieService.deleteMovie(title);
        return deletedMovie.map(movie -> "Movie deletion successful: " + formatMovie(movie))
                .orElse("Failed to delete the movie! Movie not found.");
    }

    private String formatMovie(MovieDTO movie) {
        return String.format("%s (%s, %d minutes)", movie.getTitle(), movie.getCategory(), movie.getLength());
    }
}

