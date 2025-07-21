package com.moviebookingsystem.app.dto.requestDTO.theaterRequestDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TheaterIdRequestDTO {

    @NotNull(message = "Theater ID cannot be null")
    @Min(value = 0, message = "Theater ID must be a positive number")
    private Integer theaterId;
}
