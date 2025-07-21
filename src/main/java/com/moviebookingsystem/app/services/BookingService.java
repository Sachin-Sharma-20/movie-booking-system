package com.moviebookingsystem.app.services;

import com.moviebookingsystem.app.constants.Role;
import com.moviebookingsystem.app.constants.SeatStatus;
import com.moviebookingsystem.app.dto.requestDTO.bookingRequestDTO.AddBookingRequestDTO;
import com.moviebookingsystem.app.dto.requestDTO.bookingRequestDTO.BookingIdRequestDTO;
import com.moviebookingsystem.app.exceptions.*;
import com.moviebookingsystem.app.model.Booking;
import com.moviebookingsystem.app.model.Seat;
import com.moviebookingsystem.app.model.Show;
import com.moviebookingsystem.app.model.User;
import com.moviebookingsystem.app.repository.BookingRepository;
import com.moviebookingsystem.app.repository.SeatRepository;
import jakarta.persistence.PessimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;

    private final UserService userService;
    private final ShowService showService;

    @Autowired
    public BookingService(BookingRepository bookingRepository, SeatRepository seatRepository, @Lazy UserService userService, ShowService showService) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.userService = userService;
        this.showService = showService;
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void createBooking(AddBookingRequestDTO bookingRequest) throws UserNotFoundException, SeatNotAvailableException, ShowNotFoundException {
        Show show = showService.getShowByIdHelper(bookingRequest.getBookingShowId());
        User user = userService.getUserByIdHelper(bookingRequest.getBookingUserId());

        // Pessimistic Lock to prevent concurrent modification
        Seat seat = seatRepository.findSeatForUpdate(bookingRequest.getBookingSeatId())
                .orElseThrow(() -> new SeatNotFoundException("SeatId: " + bookingRequest.getBookingSeatId() + " Not Found"));

        try{
            if (seat.getSeatStatus() == SeatStatus.BOOKED) {
                throw new SeatNotAvailableException("Seat is already booked");
            }
            if (seat.getSeatStatus() == SeatStatus.LOCKED) {
                throw new SeatNotAvailableException("Seat not available right now");
            }

            // Delay to simulate transaction execution
            try {
                Thread.sleep(500); // 500ms
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // Temporarily lock the seat
            seat.setSeatStatus(SeatStatus.LOCKED);
            seatRepository.saveAndFlush(seat);

            // Create Booking
            Booking booking = new Booking();
            booking.setBookingUser(user);
            booking.setBookingShow(show);
            booking.setBookingSeat(seat);
            bookingRepository.saveAndFlush(booking);

            // Mark seat as booked
            seat.setSeatStatus(SeatStatus.BOOKED);
        }
        catch(PessimisticLockException e){
            throw new SeatNotAvailableException("Seat has already been booked by another user");
        }
        finally {
            seatRepository.saveAndFlush(seat);
        }
    }


    @Transactional
    public void deleteBooking(BookingIdRequestDTO bookingIdRequest) throws BookingNotFoundException ,UnauthorizedException{
        //pessimistic locks on booking and seat
        Booking booking = bookingRepository.findBookingForUpdate(bookingIdRequest.getBookingId()).orElseThrow(()-> new BookingNotFoundException("Booking Id: "+bookingIdRequest.getBookingId()+"not found"));
        Seat seat = seatRepository.findSeatForUpdate(booking.getBookingSeat().getSeatId()).orElseThrow(()-> new SeatNotFoundException("Seat Id:"+booking.getBookingSeat().getSeatId()+" Not Found"));

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.getUserByUsernameHelper(username);
            if(!user.getRole().equals(Role.ADMIN) && !booking.getBookingUser().getUsername().equals(username)) {
                throw new UnauthorizedException("You do not have permission to delete this booking");
            }
            seat.setSeatStatus(SeatStatus.UNLOCKED);
            seatRepository.save(seat);
            bookingRepository.delete(booking);
        }
        catch (OptimisticLockingFailureException e){
            throw new ConcurrentModificationException("Deletion of Booking failed due to concurrent modification");
        }
    }


    public Booking getBookingByIdHelper(Integer bookingId) throws BookingNotFoundException {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if(booking.isEmpty()){
            throw new BookingNotFoundException("Booking id: " + bookingId + " not found");
        }
        return booking.get();
    }

    public Booking getBookingById(BookingIdRequestDTO bookingIdRequest) throws BookingNotFoundException {
        return getBookingByIdHelper(bookingIdRequest.getBookingId());
    }

    public Seat getBookingSeatById(BookingIdRequestDTO bookingIdRequest) throws BookingNotFoundException {
        return getBookingByIdHelper(bookingIdRequest.getBookingId()).getBookingSeat();
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
