package com.moviebookingsystem.app.controller;


import com.moviebookingsystem.app.dto.requestDTO.seatRequestDTO.SeatIdRequestDTO;
import com.moviebookingsystem.app.dto.requestDTO.seatRequestDTO.SeatNumberRequestDTO;
import com.moviebookingsystem.app.dto.requestDTO.seatRequestDTO.SeatRequestDTO;
import com.moviebookingsystem.app.services.SeatService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/getSeat")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getSeat(@Valid @ModelAttribute SeatIdRequestDTO seatIdRequest) {
        return new ResponseEntity<>(seatService.getSeat(seatIdRequest), HttpStatus.OK);
    }

    @GetMapping("/getSeatNumber")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getSeatNumber(@Valid @ModelAttribute SeatIdRequestDTO seatIdRequest) {
        return new ResponseEntity<>(seatService.getSeatNumber(seatIdRequest), HttpStatus.OK);
    }

    @GetMapping("/getSeatStatus")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    private ResponseEntity<?> getSeatStatus(@Valid @ModelAttribute SeatIdRequestDTO seatIdRequest) {
        return new ResponseEntity<>(seatService.getSeatStatus(seatIdRequest), HttpStatus.OK);
    }

    @GetMapping("/getAllSeats")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getAllSeats() {
        return new ResponseEntity<>(seatService.getAllSeats(), HttpStatus.OK);
    }
}
