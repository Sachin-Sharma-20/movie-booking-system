package com.moviebookingsystem.app.dto.requestDTO.seatRequestDTO;

import com.moviebookingsystem.app.constants.SeatStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class SeatNumberRequestDTO {
    @NotNull(message = "Seat number cannot be null")
    @Range(min = 1, max = 100, message = "Seat number must be between 1 and 100")
    private Integer seatNumber;

}
