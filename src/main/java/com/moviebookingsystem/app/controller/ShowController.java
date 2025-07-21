package com.moviebookingsystem.app.controller;


import com.moviebookingsystem.app.dto.requestDTO.showRequestDTO.*;
import com.moviebookingsystem.app.model.Show;
import com.moviebookingsystem.app.services.ShowService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
public class ShowController {
    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping("/getShow")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getShowById(@Valid @ModelAttribute ShowIdRequestDTO showIdRequest) {
        return new ResponseEntity<>(showService.getShowById(showIdRequest), HttpStatus.OK);
    }

    @GetMapping("/getAllShows")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<List<Show>> getAllShows() {
        return new ResponseEntity<>(showService.getAllShows(), HttpStatus.OK);
    }

    @GetMapping("/getShowBookings")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getShowBookings(@Valid @ModelAttribute ShowIdRequestDTO showIdRequest) {
        return new ResponseEntity<>(showService.getBookingsByShowId(showIdRequest), HttpStatus.OK);
    }


    @PostMapping("/addShow")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addShow(@Valid @RequestBody AddShowRequestDTO addShowRequest) {
        showService.addShow(addShowRequest);
        return new ResponseEntity<>("Show add successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteShow")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteShow(@Valid @ModelAttribute ShowIdRequestDTO showIdRequest) {
        showService.deleteShow(showIdRequest);
        return new ResponseEntity<>("Show delete successfully",HttpStatus.OK);
    }

    @PutMapping("/modifyShow")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> modifyShow(@Valid @RequestBody ModifyShowRequestDTO modifyShowRequest) {
        showService.modifyShow(modifyShowRequest);
        return new ResponseEntity<>("Show modified successfully",HttpStatus.OK);
    }

    @DeleteMapping("/deleteShowBooking")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteShowBooking(@Valid @RequestBody DeleteBookingForShowRequestDTO deleteBookingForShowRequest) {
        showService.deleteShowBooking(deleteBookingForShowRequest);
        return new ResponseEntity<>("Booking for show " + deleteBookingForShowRequest.getShowId() + " deleted successfully",HttpStatus.OK);
    }

    @GetMapping("/getAllSeats")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public ResponseEntity<?> getAllSeats(@Valid @ModelAttribute ShowIdRequestDTO showIdRequest) {
        return new ResponseEntity<>(showService.getAllSeats(showIdRequest), HttpStatus.OK);
    }

    @PostMapping("/addSeats")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addSeats(@Valid @RequestBody ShowIdAndSeatsRequestDTO showIdAndSeatRequest){
        showService.addSeats(showIdAndSeatRequest);
        return new ResponseEntity<>("Seats added successfully",HttpStatus.CREATED);
    }

}
