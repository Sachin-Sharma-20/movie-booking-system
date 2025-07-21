package com.moviebookingsystem.app.dto.responseDTO;

import lombok.Data;

@Data
public class BookingResponseDTO {
    private Integer bookingId;
    private UserResponseDTO bookingUser;
    private ShowResponseDTO bookingShow;
    private SeatResponseDTO bookingSeat;
}
