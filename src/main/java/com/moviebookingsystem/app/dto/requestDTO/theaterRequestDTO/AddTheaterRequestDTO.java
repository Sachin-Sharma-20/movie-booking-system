package com.moviebookingsystem.app.dto.requestDTO.theaterRequestDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class AddTheaterRequestDTO {

    @NotBlank(message = "Theater name cannot be empty")
    @Size(min = 2, max = 100, message = "Theater name must be between 2 and 100 characters")
    private String theaterName;

    private List<Integer> theaterShows;
}
