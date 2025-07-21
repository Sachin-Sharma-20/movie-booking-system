package com.moviebookingsystem.app.dto.requestDTO.showRequestDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ShowRequestDTO {

    @NotNull(message = "Show ID cannot be null")
    @Min(value = 0, message = "Show ID must be a positive number")
    private Integer showId;

    @NotNull(message = "Theater ID cannot be null")
    @Min(value = 0, message = "Theater ID must be a positive number")
    private Integer showTheaterId;

    @NotNull(message = "Movie ID cannot be null")
    @Min(value = 0, message = "Movie ID must be a positive number")
    private Integer showMovieId;

    @NotNull(message = "Bookings list cannot be null")
    @Valid
    private List<Integer> showBookings;
}
