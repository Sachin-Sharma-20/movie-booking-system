package com.moviebookingsystem.app.services;


import com.moviebookingsystem.app.constants.SeatStatus;
import com.moviebookingsystem.app.dto.requestDTO.seatRequestDTO.SeatIdRequestDTO;
import com.moviebookingsystem.app.dto.requestDTO.seatRequestDTO.SeatNumberRequestDTO;
import com.moviebookingsystem.app.dto.requestDTO.seatRequestDTO.SeatRequestDTO;
import com.moviebookingsystem.app.exceptions.SeatNotFoundException;
import com.moviebookingsystem.app.model.Seat;
import com.moviebookingsystem.app.repository.SeatRepository;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeatService {
    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Seat getSeatHelper(String seatId) throws SeatNotFoundException {
        Optional<Seat> seat = seatRepository.findById(seatId);
        if(seat.isEmpty()){
            throw new SeatNotFoundException("Seat Id: " + seatId + " not found");
        }
        return seat.get();
    }
    public Seat getSeat(SeatIdRequestDTO seatIdRequest) throws  SeatNotFoundException {
        return getSeatHelper(seatIdRequest.getSeatId());
    }
    public Integer getSeatNumber(SeatIdRequestDTO seatIdRequest) throws  SeatNotFoundException {
        return getSeatHelper(seatIdRequest.getSeatId()).getSeatNumber();
    }
    public SeatStatus getSeatStatus(SeatIdRequestDTO seatIdRequest) throws  SeatNotFoundException {
        return getSeatHelper(seatIdRequest.getSeatId()).getSeatStatus();
    }

    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }

}
