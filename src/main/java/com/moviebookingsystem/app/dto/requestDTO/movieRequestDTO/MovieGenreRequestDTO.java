package com.moviebookingsystem.app.dto.requestDTO.movieRequestDTO;

import com.moviebookingsystem.app.constants.Genre;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovieGenreRequestDTO {
    @NotNull(message = "Genre cannot be null")
    private Genre movieGenre;
}
