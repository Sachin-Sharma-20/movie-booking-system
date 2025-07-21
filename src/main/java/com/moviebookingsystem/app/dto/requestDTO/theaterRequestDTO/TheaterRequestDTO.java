package com.moviebookingsystem.app.dto.requestDTO.theaterRequestDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class TheaterRequestDTO {

    @NotNull(message = "Theater ID cannot be null")
    @Min(value = 0, message = "Theater ID must be a positive number")
    private Integer theaterId;

    @NotBlank(message = "Theater name cannot be empty")
    @Size(min = 2, max = 100, message = "Theater name must be between 2 and 100 characters")
    private String theaterName;

    @NotNull(message = "Shows list cannot be null")
    @Valid
    private List<Integer> theaterShows;
}
