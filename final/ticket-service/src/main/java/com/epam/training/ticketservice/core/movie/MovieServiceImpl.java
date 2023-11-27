package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.Movie;
import com.epam.training.ticketservice.core.movie.persistence.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    @Override
    public void createMovie(String title, String genre, Integer duration) {
        Movie newmovie = new Movie(title, genre, duration);
        movieRepository.save(newmovie);
    }

    @Override
    public Optional<MovieDto> updateMovie(String title, String category, Integer length) {
        return movieRepository.findByTitle(title)
                .map(movie -> {
                    movie.setCategory(category);
                    movie.setLength(length);
                    movieRepository.save(movie);
                    return new MovieDto(movie.getTitle(), movie.getCategory(), movie.getLength());
                });
    }

    @Override
    public Optional<MovieDto> deleteMovie(String title) {
        return movieRepository.findByTitle(title)
                .map(movie -> {
                    movieRepository.delete(movie);
                    return new MovieDto(movie.getTitle(), movie.getCategory(), movie.getLength());
                });
    }

    @Override
    public List<MovieDto> listMovies() {
        return movieRepository.findAll().stream()
                .map(movie -> new MovieDto(movie.getTitle(), movie.getCategory(), movie.getLength()))
                .collect(Collectors.toList());
    }
}
