package com.moviebookingsystem.app.dto.requestDTO.seatRequestDTO;

import com.moviebookingsystem.app.constants.SeatStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class SeatRequestDTO {
    @NotNull(message = "Seat ID cannot be null")
    @Size(min = 1, max = 100, message = "Seat ID must be between 1 and 100 characters")
    private String seatId;

    @NotNull(message = "Seat number cannot be null")
    @Range(min = 1, max = 100, message = "Seat number must be between 1 and 100")
    private Integer seatNumber;

    @NotNull(message = "Seat status cannot be null")
    @Size(min = 6, max = 8, message = "Seat Status must be valid")
    private SeatStatus seatStatus;
}
