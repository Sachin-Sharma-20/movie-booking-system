package com.moviebookingsystem.app.repository;

import com.moviebookingsystem.app.model.Seat;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s from Seat s WHERE s.seatId= :seatId")
    Optional<Seat> findSeatForUpdate(String seatId);
}