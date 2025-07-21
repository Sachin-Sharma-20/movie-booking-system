package com.moviebookingsystem.app.dto.requestDTO.userRequestDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsernameRequestDTO {
    @NotNull(message = "Username is required")
    @Size(min = 3,max = 20,message = "Username must be between 3 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$",message = "Username should only contains letters, numbers and underscore")
    private String username;
}
