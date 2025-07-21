package com.moviebookingsystem.app.dto.responseDTO;

import lombok.Data;

import java.util.List;

@Data
public class ShowResponseDTO {
    private Integer showId;
    private TheaterResponseDTO theaterResponse;
    private MovieResponseDTO movieResponse;
    private List<BookingResponseDTO> bookingResponses;
}
