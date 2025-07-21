package com.moviebookingsystem.app.model;

import com.moviebookingsystem.app.constants.Genre;
import com.moviebookingsystem.app.constants.Language;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    @Column(nullable = false, unique = true)
    private String movieTitle;

    @Column(nullable = false)
    private Integer movieReleaseYear;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genre movieGenre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language movieLanguage;

    @Column(nullable = false)
    private Integer movieDuration;
}
