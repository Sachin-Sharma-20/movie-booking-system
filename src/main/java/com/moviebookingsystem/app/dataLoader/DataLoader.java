package com.moviebookingsystem.app.dataLoader;

import com.moviebookingsystem.app.constants.Genre;
import com.moviebookingsystem.app.constants.Language;
import com.moviebookingsystem.app.constants.Role;
import com.moviebookingsystem.app.constants.SeatStatus;
import com.moviebookingsystem.app.model.*;
import com.moviebookingsystem.app.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;
    private final ShowRepository showRepository;
    private final SeatRepository seatRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, MovieRepository movieRepository,
                      TheaterRepository theaterRepository, ShowRepository showRepository,
                      SeatRepository seatRepository, BookingRepository bookingRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
        this.showRepository = showRepository;
        this.seatRepository = seatRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // ✅ 1. Add User
        User user = new User();
        user.setUsername("john_doe");
        user.setFirstname("John");
        user.setLastname("Doe");
        String password = passwordEncoder.encode("password123");
        user.setPassword(password);
        user.setRole(Role.USER);
        user = userRepository.save(user);
        System.out.println("✅ User Created: " + user.getUsername());

        User user1 = new User();
        user1.setUsername("sach");
        user1.setFirstname("sachin");
        user1.setLastname("sharma");
        user1.setPassword(password);
        user1.setRole(Role.ADMIN);
        user1 = userRepository.save(user1);
        System.out.println("✅ User Created: " + user1.getUsername());

        // ✅ 2. Add Movie
        Movie movie = new Movie();
        movie.setMovieTitle("Inception");
        movie.setMovieReleaseYear(2010);
        movie.setMovieGenre(Genre.THRILLER);
        movie.setMovieLanguage(Language.ENGLISH);
        movie.setMovieDuration(148);
        movie = movieRepository.save(movie);
        System.out.println("✅ Movie Added: " + movie.getMovieTitle());

        // ✅ 3. Add Theater
        Theater theater = new Theater();
        theater.setTheaterName("IMAX Downtown");
        theater = theaterRepository.save(theater);
        System.out.println("✅ Theater Added: " + theater.getTheaterName());

        // ✅ 4. Add Show
        Show show = new Show();
        show.setShowTheater(theater);
        show.setShowMovie(movie);
        show.setShowSeatCount(10);
        show = showRepository.save(show);
        System.out.println("✅ Show Created for " + movie.getMovieTitle() + " at " + theater.getTheaterName());

        // ✅ 5. Add Seats to Show
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Seat seat = new Seat();
            seat.setSeatNumber(i);
            seat.setSeatShow(show);
            seat.setSeatStatus(SeatStatus.UNLOCKED);
            seats.add(seat);
        }
        seatRepository.saveAll(seats);
        System.out.println("✅ 10 Seats Added to Show");

        // ✅ 6. Create Booking for User
//        Seat seatToBook = seats.getFirst(); // Pick Seat 1
//        seatToBook.setSeatStatus(SeatStatus.BOOKED);
//        seatRepository.save(seatToBook);

//        Booking booking = new Booking();
//        booking.setBookingUser(user);
//        booking.setBookingShow(show);
//        booking.setBookingSeat(seatToBook);
//        bookingRepository.save(booking);
//        System.out.println("✅ Booking Created for " + user.getUsername() + " on Seat " + seatToBook.getSeatNumber());
    }
}
