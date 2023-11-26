package com.epam.training.ticketservice.core.movie;

import com.epam.training.ticketservice.core.movie.model.MovieDTO;
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
    public void addMovie(String title, String genre, Integer duration) {
        Movie movie = new Movie(title, genre, duration);
        movieRepository.save(movie);
    }

    @Override
    public Optional<MovieDTO> updateMovie(String title, String genre, Integer duration) {
        Optional<Movie> optionalMovie = movieRepository.findByTitle(title);

        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            movie.setCategory(genre);
            movie.setLength(duration);
            movieRepository.save(movie);

            MovieDTO updatedMovieDto = new MovieDTO(movie.getTitle(), movie.getCategory(), movie.getLength());
            return Optional.of(updatedMovieDto);
        } else {
            return Optional.empty();
        }
    }


    @Override
    public Optional<MovieDTO> deleteMovie(String title) {
        Optional<Movie> optionalMovie = movieRepository.findByTitle(title);

        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            movieRepository.delete(movie);

            MovieDTO deletedMovieDto = new MovieDTO(movie.getTitle(), movie.getCategory(), movie.getLength());
            return Optional.of(deletedMovieDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<MovieDTO> listMovies() {
        return movieRepository.findAll().stream()
                .map(movie -> new MovieDTO(movie.getTitle(), movie.getCategory(), movie.getLength()))
                .collect(Collectors.toList());
    }
}
