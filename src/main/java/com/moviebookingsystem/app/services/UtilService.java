package com.moviebookingsystem.app.services;

import com.moviebookingsystem.app.dto.requestDTO.seatRequestDTO.SeatIdRequestDTO;
import com.moviebookingsystem.app.dto.requestDTO.userRequestDTO.UsernameRequestDTO;
import com.moviebookingsystem.app.exceptions.SeatNotFoundException;
import com.moviebookingsystem.app.exceptions.ShowNotFoundException;
import com.moviebookingsystem.app.exceptions.UserNotFoundException;
import com.moviebookingsystem.app.model.Seat;
import com.moviebookingsystem.app.model.Show;
import com.moviebookingsystem.app.model.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class UtilService {
    private final SeatService seatService;
    private final UserService userService;
    private final ShowService showService;

    public UtilService(@Lazy ShowService showService, SeatService seatService, UserService userService) {
        this.showService = showService;
        this.seatService = seatService;
        this.userService = userService;
    }

    public Show getShowById(Integer showId) throws ShowNotFoundException {
        return showService.getShowByIdHelper(showId);
    }

    public User getUserByUsername(UsernameRequestDTO usernameRequest) throws UserNotFoundException {
        return userService.getUserByUsername(usernameRequest);
    }

    public Seat getSeat(SeatIdRequestDTO seatIdRequest) throws SeatNotFoundException {
        return seatService.getSeat(seatIdRequest);
    }
}
