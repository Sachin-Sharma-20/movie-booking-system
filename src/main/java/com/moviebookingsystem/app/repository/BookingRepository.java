package com.moviebookingsystem.app.repository;

import com.moviebookingsystem.app.model.Booking;
import com.moviebookingsystem.app.model.Seat;
import jakarta.persistence.LockModeType;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b from Booking b WHERE b.bookingId= :bookingId")
    Optional<Booking> findBookingForUpdate(Integer bookingId);
}