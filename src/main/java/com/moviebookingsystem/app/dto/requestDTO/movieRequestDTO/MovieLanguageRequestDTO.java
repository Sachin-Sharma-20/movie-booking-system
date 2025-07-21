package com.moviebookingsystem.app.dto.requestDTO.movieRequestDTO;

import com.moviebookingsystem.app.constants.Language;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovieLanguageRequestDTO {

    @NotNull(message = "Language cannot be null")
    private Language movieLanguage;
}
