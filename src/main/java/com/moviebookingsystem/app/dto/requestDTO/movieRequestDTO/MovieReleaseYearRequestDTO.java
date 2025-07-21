package com.moviebookingsystem.app.dto.requestDTO.movieRequestDTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovieReleaseYearRequestDTO {

    @NotNull(message = "Year cannot be null")
    @Min(value = 1888, message = "Year must be 1888 or later")  // 1888 = first known film
    @Max(value = 2025, message = "Year must be a valid future year")
    private Integer movieReleaseYear;
}
