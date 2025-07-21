package com.moviebookingsystem.app.dto.requestDTO.theaterRequestDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddShowForTheaterRequestDTO {

    @NotNull(message = "Theater ID cannot be null")
    @Min(value = 0, message = "Theater ID must be a positive number")
    private Integer theaterId;

    @NotNull(message = "Show details cannot be null")
    @Valid
    private Integer theaterShowId;
}
