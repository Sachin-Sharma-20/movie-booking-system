package com.moviebookingsystem.app.controller;


import com.moviebookingsystem.app.dto.requestDTO.userRequestDTO.LoginUserRequestDTO;
import com.moviebookingsystem.app.dto.requestDTO.userRequestDTO.RegisterUserRequestDTO;
import com.moviebookingsystem.app.dto.requestDTO.userRequestDTO.UserIdRequestDTO;
import com.moviebookingsystem.app.dto.requestDTO.userRequestDTO.UsernameRequestDTO;
import com.moviebookingsystem.app.exceptions.UnauthorizedException;
import com.moviebookingsystem.app.exceptions.UserAlreadyRegisteredException;
import com.moviebookingsystem.app.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(summary = "Get a user",description = "Fetch a given user")
    @GetMapping("/getUser")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getUser(@Valid @ModelAttribute UsernameRequestDTO usernameRequest){
        return new ResponseEntity<>(userService.getUserByUsername(usernameRequest), HttpStatus.OK);
    }

    @Operation(summary = "Get all users", description = "Fetch a list of all users.")
    @GetMapping("/getAllUser")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUser(){
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @Operation(summary = "Register user", description = "Register a user with given user details.")
    @PostMapping("/registerUser")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserRequestDTO registerUserRequest) {
        try {
            String token = userService.registerUser(registerUserRequest);
            String response = "User Successfully Registered\nToken: " + token;
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (UserAlreadyRegisteredException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @Operation(summary = "Delete user", description = "Delete the given user")
    @DeleteMapping("/deleteUser")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteUser(@Valid @ModelAttribute UserIdRequestDTO userIdRequest){
        userService.deleteUser(userIdRequest);
        return new ResponseEntity<>("User Successfully Deleted",HttpStatus.OK);
    }

    @Operation(summary = "User login", description = "User login")
    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUserRequestDTO loginUserRequest) {
        //return jwt token in response
        return new ResponseEntity<>(userService.authenticate(loginUserRequest.getUsername(),loginUserRequest.getPassword()), HttpStatus.OK);
    }

    @Operation(summary = "Get all bookings of a user", description = "Get all bookings of a given user")
    @GetMapping("/getBookings")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getBookings(@Valid @ModelAttribute UserIdRequestDTO userIdRequest) {
        return new ResponseEntity<>(userService.getBookings(userIdRequest),HttpStatus.OK);
    }
}
