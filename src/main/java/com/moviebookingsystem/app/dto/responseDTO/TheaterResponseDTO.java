package com.moviebookingsystem.app.dto.responseDTO;

import com.moviebookingsystem.app.model.Show;
import lombok.Data;

import java.util.List;

@Data
public class TheaterResponseDTO {
    private Integer theaterId;
    private String theaterName;
    private List<ShowResponseDTO> theaterShows;

}
