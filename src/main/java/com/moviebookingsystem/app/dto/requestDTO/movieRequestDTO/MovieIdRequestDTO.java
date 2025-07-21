package com.moviebookingsystem.app.dto.requestDTO.movieRequestDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovieIdRequestDTO {
    @NotNull(message = "Movie ID cannot be null")
    @Min(value = 0, message = "Movie ID must be a positive number")
    private Integer movieId;
}
