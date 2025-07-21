package com.moviebookingsystem.app.dto.requestDTO.seatRequestDTO;

import com.moviebookingsystem.app.constants.SeatStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class SeatIdRequestDTO {
    @NotNull(message = "Seat ID cannot be null")
    @Size(min = 1, max = 100, message = "Seat ID must be between 1 and 100 characters")
    private String seatId;
}
