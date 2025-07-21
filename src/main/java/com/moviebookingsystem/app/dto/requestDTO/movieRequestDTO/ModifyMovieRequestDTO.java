package com.moviebookingsystem.app.dto.requestDTO.movieRequestDTO;

import com.moviebookingsystem.app.constants.Genre;
import com.moviebookingsystem.app.constants.Language;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ModifyMovieRequestDTO {
    @NotNull(message = "Movie id cannot be null")
    @Min(value=0,message = "Movie ID must be a positive number")
    private Integer movieId;

    @Pattern(regexp = "^[a-zA-Z0-9 ]+$",message = "title should only contains letters, numbers and spaces")
    @Size(min = 1, max = 100, message = "Movie title must be of size between 1 and 100")
    private String movieTitle;

    @Min(value = 1888, message = "Year must be 1888 or later")  // 1888 = first known film
    @Max(value = 2025, message = "Year must be a valid future year")
    private Integer movieReleaseYear;

    private Genre movieGenre;

    private Language movieLanguage;

    private Integer movieDuration;

}
