package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;

import java.util.List;
import java.util.Optional;
public interface MovieService {
    void createMovie(String title, String category, Integer length);
    Optional<MovieDto> updateMovie(String title, String category, Integer length);
    Optional<MovieDto> deleteMovie(String title);
    List<MovieDto> listMovies();
}