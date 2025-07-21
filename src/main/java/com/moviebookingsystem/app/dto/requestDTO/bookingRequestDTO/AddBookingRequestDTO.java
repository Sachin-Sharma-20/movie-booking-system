package com.moviebookingsystem.app.dto.requestDTO.bookingRequestDTO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddBookingRequestDTO {

    @NotNull(message = "User ID cannot be null")
    @Min(value = 0, message = "User ID must be a positive number")
    private Integer bookingUserId;

    @NotNull(message = "Show ID cannot be null")
    @Min(value = 0, message = "Show ID must be a positive number")
    private Integer bookingShowId;

    @NotNull(message = "Seat ID cannot be null")
    @Size(min = 1, max = 100, message = "Seat ID must be between 1 and 100 characters")
    private String bookingSeatId;

    public AddBookingRequestDTO(Integer bUserid,Integer bShowid, String bSeatid) {
        bookingUserId = bUserid;
        bookingShowId = bShowid;
        bookingSeatId = bSeatid;
    }
}
