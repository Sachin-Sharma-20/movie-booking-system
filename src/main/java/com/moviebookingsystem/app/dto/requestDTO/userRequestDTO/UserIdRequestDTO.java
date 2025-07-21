package com.moviebookingsystem.app.dto.requestDTO.userRequestDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserIdRequestDTO {
    @NotNull(message = "User ID cannot be null")
    @Min(value = 0, message = "User ID must be a positive number")
    private Integer userId;
}
