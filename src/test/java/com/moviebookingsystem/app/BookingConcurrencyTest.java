package com.moviebookingsystem.app;

import com.moviebookingsystem.app.constants.SeatStatus;
import com.moviebookingsystem.app.dto.requestDTO.bookingRequestDTO.AddBookingRequestDTO;
import com.moviebookingsystem.app.exceptions.SeatNotAvailableException;
import com.moviebookingsystem.app.repository.SeatRepository;
import com.moviebookingsystem.app.services.BookingService;
import com.moviebookingsystem.app.services.SeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookingConcurrencyTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private SeatRepository seatRepository;

//    @BeforeEach
//    void resetSeatState() {
//        seatRepository.findSeatForUpdate("seat_1_show_1")
//                .ifPresent(seat -> {
//                    seat.setSeatStatus(SeatStatus.UNLOCKED);
//                    seatRepository.save(seat);
//                });
//    }

    @Test
    public void testConcurrentBooking() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(1);

        Callable<String> user1 = () -> {
            latch.await(); // Ensure both start at the same time
            try {
                bookingService.createBooking(new AddBookingRequestDTO(1, 1, "seat_1_show_1"));
                return "User 1 Successfully Booked!";
            } catch (Exception e) {
                return "User 1 Failed: " + e.getMessage();
            }
        };

        Callable<String> user2 = () -> {
            latch.await(); // Ensure both start at the same time
            try {
                bookingService.createBooking(new AddBookingRequestDTO(2, 1, "seat_1_show_1"));
                return "User 2 Successfully Booked!";
            } catch (Exception e) {
                return "User 2 Failed: " + e.getMessage();
            }
        };

        Future<String> result1 = executorService.submit(user1);
        Future<String> result2 = executorService.submit(user2);

        latch.countDown(); // 🔥 Start both threads simultaneously

        System.out.println(result1.get());
        System.out.println(result2.get());

        executorService.shutdown();
    }


}
