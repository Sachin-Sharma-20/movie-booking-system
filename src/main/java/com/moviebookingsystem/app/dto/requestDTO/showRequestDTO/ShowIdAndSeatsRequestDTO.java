package com.moviebookingsystem.app.dto.requestDTO.showRequestDTO;

import com.moviebookingsystem.app.dto.requestDTO.seatRequestDTO.SeatIdRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ShowIdAndSeatsRequestDTO {

    @NotNull(message = "Show ID cannot be null")
    @Min(value = 0, message = "Show ID must be a positive number")
    private Integer showId;

    @NotNull(message = "Seats cannot be null")
    @Min(value = 0, message = "Seat count must be a positive number")
    private Integer showSeatCount;
}
