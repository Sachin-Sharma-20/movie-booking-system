package com.moviebookingsystem.app.services;

import com.moviebookingsystem.app.constants.Genre;
import com.moviebookingsystem.app.constants.Language;
import com.moviebookingsystem.app.dto.requestDTO.movieRequestDTO.*;
import com.moviebookingsystem.app.exceptions.MovieAlreadyExistsException;
import com.moviebookingsystem.app.exceptions.MovieNotFoundException;
import com.moviebookingsystem.app.model.Movie;
import com.moviebookingsystem.app.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieByIdHelper(Integer movieId) throws MovieNotFoundException {
        Optional<Movie> movie = movieRepository.findById(movieId);
        if(movie.isEmpty()){
            throw new MovieNotFoundException("Movie ID:"+ movieId +" not found");
        }
        return movie.get();
    }

    public Movie getMovieById(MovieIdRequestDTO movieIdRequest) throws MovieNotFoundException {
        return getMovieByIdHelper(movieIdRequest.getMovieId());
    }

    public List<Movie> getMoviesByTitle(MovieTitleRequestDTO movieTitleRequest) {
        return movieRepository.findByMovieTitle(movieTitleRequest.getMovieTitle());
    }
    public List<Movie> getMoviesByReleaseYear(MovieReleaseYearRequestDTO movieReleaseYearRequest) {
        return movieRepository.findByMovieReleaseYear(movieReleaseYearRequest.getMovieReleaseYear());
    }
    public List<Movie> getMoviesByGenre(MovieGenreRequestDTO movieGenreRequest) {
        return movieRepository.findByMovieGenre(movieGenreRequest.getMovieGenre());
    }
    public List<Movie> getMoviesByLanguage(MovieLanguageRequestDTO movieLanguageRequest) {
        return movieRepository.findByMovieLanguage(movieLanguageRequest.getMovieLanguage());
    }

    public void addMovie(AddMovieRequestDTO addMovieRequest) throws MovieAlreadyExistsException {
        if(movieRepository.existsByMovieTitleAndMovieReleaseYearAndMovieGenreAndMovieLanguage(addMovieRequest.getMovieTitle(), addMovieRequest.getMovieReleaseYear(),addMovieRequest.getMovieGenre(),addMovieRequest.getMovieLanguage())){
             throw new MovieAlreadyExistsException("Movie already exists");
        };

        Movie movie = new Movie();

        movie.setMovieTitle(addMovieRequest.getMovieTitle());
        movie.setMovieReleaseYear(addMovieRequest.getMovieReleaseYear());
        movie.setMovieGenre(addMovieRequest.getMovieGenre());
        movie.setMovieLanguage(addMovieRequest.getMovieLanguage());
        movie.setMovieDuration(addMovieRequest.getMovieDuration());

        movieRepository.save(movie);
    }

    public void deleteMovie(MovieIdRequestDTO movieIdRequest) throws MovieNotFoundException {
        Movie movie = getMovieById(movieIdRequest);
        movieRepository.delete(movie);
    }

    public void modifyMovie(ModifyMovieRequestDTO movieRequest) throws MovieNotFoundException {
        Movie nonUpdatedMovie = getMovieByIdHelper(movieRequest.getMovieId());
        if(movieRequest.getMovieReleaseYear()!=null) nonUpdatedMovie.setMovieReleaseYear(movieRequest.getMovieReleaseYear());
        if (movieRequest.getMovieTitle()!=null) nonUpdatedMovie.setMovieTitle(movieRequest.getMovieTitle());
        if(movieRequest.getMovieLanguage()!=null) nonUpdatedMovie.setMovieLanguage(movieRequest.getMovieLanguage());
        if(movieRequest.getMovieGenre()!=null) nonUpdatedMovie.setMovieGenre(movieRequest.getMovieGenre());
        if(movieRequest.getMovieDuration()!=null) nonUpdatedMovie.setMovieDuration(movieRequest.getMovieDuration());
    }

}
