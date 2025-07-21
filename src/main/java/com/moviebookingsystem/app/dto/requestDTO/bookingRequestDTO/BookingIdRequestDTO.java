package com.moviebookingsystem.app.dto.requestDTO.bookingRequestDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingIdRequestDTO {
    @NotNull(message = "Booking ID cannot be null")
    @Min(value = 0, message = "Booking ID must be a positive number")
    private Integer bookingId;

}
