package com.moviebookingsystem.app.controller;

import com.moviebookingsystem.app.dto.requestDTO.bookingRequestDTO.AddBookingRequestDTO;
import com.moviebookingsystem.app.dto.requestDTO.bookingRequestDTO.BookingIdRequestDTO;
import com.moviebookingsystem.app.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/getBooking")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getBookingById(@Valid @ModelAttribute BookingIdRequestDTO bookingIdRequest){
        return new ResponseEntity<>(bookingService.getBookingById(bookingIdRequest), HttpStatus.OK);
    }

    @GetMapping("/getBookingSeat")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getBookingSeatById(@Valid @ModelAttribute BookingIdRequestDTO bookingIdRequest) {

        return new ResponseEntity<>(bookingService.getBookingSeatById(bookingIdRequest), HttpStatus.OK);
    }

    @GetMapping("/getAllBookings")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllBookings(){
        return new ResponseEntity<>(bookingService.getAllBookings(),HttpStatus.OK);
    }

    @PostMapping("/createBooking")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createBooking(@Valid @RequestBody AddBookingRequestDTO bookingRequest) {
        bookingService.createBooking(bookingRequest);
        return new ResponseEntity<>("Booking successfully added",HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteBooking")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteBooking(@Valid @ModelAttribute BookingIdRequestDTO bookingIdRequest) {
        bookingService.deleteBooking(bookingIdRequest);
        return new ResponseEntity<>("Booking successfully deleted",HttpStatus.OK);
    }

}
