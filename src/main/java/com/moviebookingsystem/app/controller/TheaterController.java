package com.moviebookingsystem.app.controller;


import com.moviebookingsystem.app.dto.requestDTO.theaterRequestDTO.*;
import com.moviebookingsystem.app.model.Theater;
import com.moviebookingsystem.app.services.TheaterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theaters")
public class TheaterController {
    private final TheaterService theaterService;

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @GetMapping("/getTheater")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getTheaterById(@Valid @ModelAttribute TheaterIdRequestDTO theaterIdRequest) {
        return new ResponseEntity<>(theaterService.getTheaterById(theaterIdRequest), HttpStatus.OK);
    }

    @GetMapping("/getAllTheaters")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Theater>> getAllTheaters() {
        return new ResponseEntity<>(theaterService.getAllTheaters(), HttpStatus.OK);
    }


    @GetMapping("/getTheatersByName")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getTheaterByName(@Valid @ModelAttribute TheaterNameRequestDTO theaterNameRequest) {
        return new ResponseEntity<>(theaterService.getTheaterByTheaterName(theaterNameRequest), HttpStatus.OK);
    }

    @GetMapping("/getAllShowsOfATheater")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> getAllShowsByTheaterId(@Valid @ModelAttribute TheaterIdRequestDTO theaterIdRequest) {
        return new ResponseEntity<>(theaterService.getAllShowsByTheaterId(theaterIdRequest), HttpStatus.OK);
    }

    @PostMapping("/addTheaterShow")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addTheaterShow(@Valid @RequestBody AddShowForTheaterRequestDTO addShowForTheaterRequest) {
        theaterService.addTheaterShow(addShowForTheaterRequest);
        return new ResponseEntity<>("Theater show successfully added",HttpStatus.OK);
    }

    @DeleteMapping("/deleteTheaterShow")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteTheaterShow(@Valid @RequestBody DeleteShowForTheaterRequestDTO deleteShowForTheaterRequest) {
        theaterService.deleteTheaterShow(deleteShowForTheaterRequest);
        return new ResponseEntity<>("Theater show successfully deleted",HttpStatus.OK);
    }

    @PostMapping("/addTheater")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addTheater(@Valid @RequestBody AddTheaterRequestDTO addTheaterDTO) {
        theaterService.addTheater(addTheaterDTO);
        return new ResponseEntity<>("Theater successfully added",HttpStatus.OK);
    }

    @DeleteMapping("/deleteTheater")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteTheater(@Valid @ModelAttribute TheaterIdRequestDTO theaterIdRequest) {
        theaterService.deleteTheaterById(theaterIdRequest);
        return new ResponseEntity<>("Theater successfully deleted",HttpStatus.OK);
    }

    @PutMapping("/modifyTheaterName")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> modifyTheaterName(@Valid @RequestBody ModifyTheaterNameRequestDTO modifyTheaterNameRequest) {
        theaterService.modifyTheaterName(modifyTheaterNameRequest);
        return new ResponseEntity<>("Theater successfully modified",HttpStatus.OK);
    }
}
