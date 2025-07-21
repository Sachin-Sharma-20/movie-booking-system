package com.moviebookingsystem.app.dto.requestDTO.movieRequestDTO;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MovieTitleRequestDTO {
    @NotBlank(message = "Title cannot be empty")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$",message = "title should only contains letters, numbers and spaces")
    private String movieTitle;
}
