package com.moviebookingsystem.app.dto.requestDTO.userRequestDTO;

import com.moviebookingsystem.app.constants.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequestDTO {
    @NotNull(message = "Username is required")
    @Size(min = 3,max = 20,message = "Username must be between 3 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$",message = "Username should only contains letters, numbers and underscore")
    private String username;

    @NotNull(message = "Password is required")
    @Size(min = 3, max = 20, message = "Password must be between 3 and 20 characters")
    private String password;


    @NotNull(message = "First name is required")
    @Pattern(regexp = "^[a-zA-Z]+$",message = "First name should only contains letters")
    private String firstname;

    @NotNull(message = "Last name is required")
    @Pattern(regexp = "^[a-zA-Z]+$",message = "Last name should only contains letters")
    private String lastname;

    private Role role = Role.USER;
}
