package com.moviebookingsystem.app.dto.responseDTO;

import com.moviebookingsystem.app.model.Booking;
import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private List<BookingResponseDTO> userBookings;
}
