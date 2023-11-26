package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDTO;

import java.util.List;
import java.util.Optional;
public interface MovieService {
    void addMovie(String title,String genre, Integer duration);
    Optional<MovieDTO> updateMovie(String title, String genre, Integer duration);
    Optional<MovieDTO> deleteMovie(String title);
    List<MovieDTO> listMovies();
}