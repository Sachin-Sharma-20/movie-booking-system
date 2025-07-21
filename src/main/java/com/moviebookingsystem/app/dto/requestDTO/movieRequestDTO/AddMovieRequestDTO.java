package com.moviebookingsystem.app.dto.requestDTO.movieRequestDTO;

import com.moviebookingsystem.app.constants.Genre;
import com.moviebookingsystem.app.constants.Language;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AddMovieRequestDTO {
    @NotNull(message = "Please provide movie title")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$",message = "title should only contains letters")
    @Size(min = 1, max = 100, message = "Movie title must be of size between 1 and 100")
    private String movieTitle;

    @NotNull(message = "Year cannot be null")
    @Min(value = 1888, message = "Year must be 1888 or later")  // 1888 = first known film
    @Max(value = 2025, message = "Year must be a valid future year")
    private Integer movieReleaseYear;

    @NotNull(message = "Please provide movie genre")
    private Genre movieGenre;

    @NotNull(message = "Please provide movie language")
    private Language movieLanguage;

    @NotNull(message = "Please provide movie duration")
    private Integer movieDuration;
}
