package com.moviebookingsystem.app.controller;

import com.moviebookingsystem.app.dto.requestDTO.movieRequestDTO.*;
import com.moviebookingsystem.app.model.Movie;
import com.moviebookingsystem.app.services.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/getMovie")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getMovieById(@Valid @ModelAttribute MovieIdRequestDTO movieIdRequest) {
        return new ResponseEntity<>(movieService.getMovieById(movieIdRequest), HttpStatus.OK);
    }

    @GetMapping("/getMoviesByTitle")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getMoviesByTitle(@Valid @ModelAttribute MovieTitleRequestDTO movieTitleRequest) {
        return new ResponseEntity<>(movieService.getMoviesByTitle(movieTitleRequest), HttpStatus.OK);
    }

    @GetMapping("/getMoviesByReleaseYear")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getMoviesByReleaseYear(@Valid @ModelAttribute MovieReleaseYearRequestDTO movieReleaseYearRequest) {
        return new ResponseEntity<>(movieService.getMoviesByReleaseYear(movieReleaseYearRequest), HttpStatus.OK);
    }
    @GetMapping("/getMoviesByGenre")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getMoviesByGenre(@Valid @ModelAttribute MovieGenreRequestDTO genreRequest) {
        return new ResponseEntity<>(movieService.getMoviesByGenre(genreRequest), HttpStatus.OK);
    }
    @GetMapping("/getMoviesByLanguage")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getMovieByLanguage(@Valid @ModelAttribute MovieLanguageRequestDTO languageRequest) {
        return new ResponseEntity<>(movieService.getMoviesByLanguage(languageRequest), HttpStatus.OK);
    }

    @GetMapping("/getAllMovies")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Movie>> getAllMovies() {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    @PostMapping("/addMovie")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addMovie(@Valid @RequestBody AddMovieRequestDTO movieRequest) {
        movieService.addMovie(movieRequest);
        return new ResponseEntity<>("Movie successfully added",HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteMovie")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteMovie(@Valid @ModelAttribute MovieIdRequestDTO movieIdRequest) {
        movieService.deleteMovie(movieIdRequest);
        return new ResponseEntity<>("Movie successfully deleted",HttpStatus.OK);
    }

    @PutMapping("/updateMovie")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> modifyMovie(@Valid @RequestBody ModifyMovieRequestDTO movieRequest) {
        movieService.modifyMovie(movieRequest);
        return new ResponseEntity<>("Movie successfully modified",HttpStatus.OK);
    }
}
