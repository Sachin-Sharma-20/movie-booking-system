package com.moviebookingsystem.app.dto.responseDTO;

import com.moviebookingsystem.app.constants.Genre;
import com.moviebookingsystem.app.constants.Language;
import lombok.Data;

@Data
public class MovieResponseDTO {
    private Integer movieId;
    private String movieTitle;
    private Integer movieReleaseYear;
    private Genre movieGenre;
    private Language movieLanguage;
    private Integer movieDuration;
}
