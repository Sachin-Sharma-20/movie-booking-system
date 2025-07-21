package com.moviebookingsystem.app.services;

import com.moviebookingsystem.app.constants.SeatStatus;
import com.moviebookingsystem.app.dto.requestDTO.showRequestDTO.*;
import com.moviebookingsystem.app.exceptions.*;
import com.moviebookingsystem.app.model.Booking;
import com.moviebookingsystem.app.model.Seat;
import com.moviebookingsystem.app.model.Show;
import com.moviebookingsystem.app.repository.SeatRepository;
import com.moviebookingsystem.app.repository.ShowRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {
    private final ShowRepository showRepository;

    private final MovieService movieService;
    private final TheaterService theaterService;
    private final SeatRepository seatRepository;

    public ShowService(MovieService movieService, TheaterService theaterService, ShowRepository showRepository, SeatRepository seatRepository) {
        this.movieService = movieService;
        this.theaterService = theaterService;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
    }

    public Show getShowByIdHelper(Integer showId) throws ShowNotFoundException {
        Optional<Show> show = showRepository.findById(showId);
        if(show.isEmpty()) {
            throw new ShowNotFoundException("Show not found");
        }
        return show.get();
    }

    public Show getShowById(ShowIdRequestDTO showIdRequest) throws  ShowNotFoundException {
        return getShowByIdHelper(showIdRequest.getShowId());
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public List<Booking> getBookingsByShowId(ShowIdRequestDTO showIdRequest) throws ShowNotFoundException {
        return getShowById(showIdRequest).getShowBookings();
    }

    public void addShow(AddShowRequestDTO addShowRequest) throws ShowAlreadyExistsException {
        if(showRepository.existsByShowTheater_TheaterIdAndShowMovie_MovieId(addShowRequest.getShowTheaterId(),addShowRequest.getShowMovieId())){
            throw new ShowAlreadyExistsException("Show with same movie and same theater already exists");
        }
        Show show = new Show();

        show.setShowMovie(movieService.getMovieByIdHelper(addShowRequest.getShowMovieId()));
        show.setShowTheater(theaterService.getTheaterByIdHelper(addShowRequest.getShowTheaterId()));

        showRepository.save(show);

        //TOTAL Seats per show defined in show entity
        List<Seat> seats = createSeats(show.getShowId(),1,show.getShowSeatCount());
        seats.forEach(seat -> seat.setSeatShow(show));
        seatRepository.saveAll(seats);
        show.getShowSeats().addAll(seats);
        showRepository.save(show);
    }

    public void deleteShow(ShowIdRequestDTO showIdRequest) throws ShowNotFoundException {
        Show show = getShowById(showIdRequest);
        showRepository.delete(show);
    }

    public void modifyShow(ModifyShowRequestDTO modifyShowRequest) throws ShowNotFoundException {
        Show oldShow = getShowByIdHelper(modifyShowRequest.getShowId());

        if(modifyShowRequest.getTheaterId()!=null){
            oldShow.setShowTheater(theaterService.getTheaterByIdHelper(modifyShowRequest.getTheaterId()));
        }
        if (modifyShowRequest.getMovieId()!=null){
            oldShow.setShowMovie(movieService.getMovieByIdHelper(modifyShowRequest.getMovieId()));
        }
        showRepository.save(oldShow);
    }

    public void deleteShowBooking(DeleteBookingForShowRequestDTO deleteBookingForShowRequest) throws ShowNotFoundException, BookingNotFoundException {
        Show show = getShowByIdHelper(deleteBookingForShowRequest.getShowId());
        Booking booking = show.getShowBookings().stream().filter(booking1 -> booking1.getBookingId().equals(deleteBookingForShowRequest.getBookingId())).findFirst().orElseThrow(() -> new BookingNotFoundException("Booking: " + deleteBookingForShowRequest.getBookingId() + " not found"));
        show.getShowBookings().remove(booking);
        showRepository.save(show);
    }


    public List<Seat> getAllSeats(ShowIdRequestDTO showIdRequest) throws ShowNotFoundException{
        return getShowById(showIdRequest).getShowSeats();
    }

    public void addSeats(ShowIdAndSeatsRequestDTO showIdAndSeatsRequest) throws ShowNotFoundException, SeatNotFoundException , UnauthorizedException{
        if(!isRequesterAuthorizedToAddSeats()){
            throw new UnauthorizedException("You are not authorized to add seats");
        }

        Show show = getShowByIdHelper(showIdAndSeatsRequest.getShowId());
        List<Seat> seats = createSeats(show.getShowId(),show.getShowSeatCount()+1,show.getShowSeatCount()+showIdAndSeatsRequest.getShowSeatCount());
        seats.forEach(seat -> seat.setSeatShow(show));
        seatRepository.saveAll(seats);

        show.setShowSeatCount(show.getShowSeatCount() + showIdAndSeatsRequest.getShowSeatCount());
        show.getShowSeats().addAll(seats);
        showRepository.save(show);
    }

    private List<Seat> createSeats(Integer showId,Integer start, Integer end) {
        List<Seat> seats= new ArrayList<>();
        for(int id = start; id<=end; id++){
            Seat seat = new Seat();
            seat.setSeatId("seat_" + id + "_show_" + showId);
            seat.setSeatNumber(id);
            seat.setSeatStatus(SeatStatus.UNLOCKED);
            seats.add(seat);
        }
        return seats;
    }
    private boolean isRequesterAuthorizedToAddSeats() {
        return true;
    }

}
