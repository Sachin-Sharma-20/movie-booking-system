package com.moviebookingsystem.app.dto.requestDTO.showRequestDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteBookingForShowRequestDTO {


    @NotNull(message = "Show ID cannot be null")
    @Min(value =0, message = "Show ID must be a positive number")
    private Integer showId;


    @NotNull(message = "Booking ID cannot be null")
    @Min(value = 0, message = "Booking ID must be a positive number")
    private Integer bookingId;


}
