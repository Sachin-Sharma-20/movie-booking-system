package com.moviebookingsystem.app.repository;

import com.moviebookingsystem.app.constants.Genre;
import com.moviebookingsystem.app.constants.Language;
import com.moviebookingsystem.app.model.Movie;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findByMovieTitle(String movieTitle);

    List<Movie> findByMovieReleaseYear(Integer movieReleaseYear);

    List<Movie> findByMovieGenre(Genre movieGenre);

    List<Movie> findByMovieLanguage(Language movieLanguage);

    boolean existsByMovieTitleAndMovieReleaseYearAndMovieGenreAndMovieLanguage(
            String movieTitle, Integer movieReleaseYear, Genre movieGenre, Language movieLanguage);

}