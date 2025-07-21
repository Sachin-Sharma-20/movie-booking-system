package com.moviebookingsystem.app.services;

import com.moviebookingsystem.app.constants.Role;
import com.moviebookingsystem.app.dto.requestDTO.theaterRequestDTO.*;
import com.moviebookingsystem.app.exceptions.*;
import com.moviebookingsystem.app.model.Show;
import com.moviebookingsystem.app.model.Theater;
import com.moviebookingsystem.app.model.User;
import com.moviebookingsystem.app.repository.TheaterRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TheaterService {

    private final TheaterRepository theaterRepository;

    private final UtilService utilService;
    private final UserService userService;

    public TheaterService(TheaterRepository theaterRepository, UtilService utilService, UserService userService) {
        this.theaterRepository = theaterRepository;
        this.utilService = utilService;
        this.userService = userService;
    }


    public Theater getTheaterByIdHelper(Integer theaterId) throws TheaterNotFoundException {
        Optional<Theater> theater =  theaterRepository.findById(theaterId);
        if(theater.isEmpty()){
            throw new TheaterNotFoundException("There is no such theater");
        }
        return theater.get();
    }

    public Theater getTheaterById(TheaterIdRequestDTO theaterIdRequest) throws TheaterNotFoundException {
        return getTheaterByIdHelper(theaterIdRequest.getTheaterId());
    }

    public List<Theater> getAllTheaters() {
        return theaterRepository.findAll();
    }

    public List<Theater> getTheaterByTheaterName(TheaterNameRequestDTO theaterNameRequest) {
        return theaterRepository.findByTheaterName(theaterNameRequest.getTheaterName());
    }

    public List<Show> getAllShowsByTheaterId(TheaterIdRequestDTO theaterIdRequest) throws TheaterNotFoundException {
        Theater theater = getTheaterByIdHelper(theaterIdRequest.getTheaterId());
        return theater.getTheaterShows();
    }


    public void addTheater(AddTheaterRequestDTO addTheaterRequest) throws TheaterAlreadyExistsException,ShowNotFoundException {
        if(theaterRepository.existsByTheaterName((addTheaterRequest.getTheaterName()))){
            throw new TheaterAlreadyExistsException("There is already a theater with the name " + addTheaterRequest.getTheaterName());
        }
        Theater theater = new Theater();

        theater.setTheaterName(addTheaterRequest.getTheaterName());
        List<Show> shows = new ArrayList<>();
        for(Integer showId:addTheaterRequest.getTheaterShows()){
            //show already exists in database
            Show show = utilService.getShowById(showId);
            shows.add(show);
        }
        theater.setTheaterShows(shows);
        theaterRepository.save(theater);
    }

    public void deleteTheaterById(TheaterIdRequestDTO theaterIdRequest) throws TheaterNotFoundException {
        Theater theater = getTheaterById(theaterIdRequest);
        theaterRepository.delete(theater);
    }

    public void modifyTheaterName(ModifyTheaterNameRequestDTO modifyTheaterNameRequest) throws TheaterNotFoundException ,UnauthorizedException{
        Theater updateTheater = getTheaterByIdHelper(modifyTheaterNameRequest.getTheaterId());
        updateTheater.setTheaterName(modifyTheaterNameRequest.getTheaterName());
        theaterRepository.save(updateTheater);
    }

    public void addTheaterShow(AddShowForTheaterRequestDTO addShowForTheaterRequest) throws TheaterNotFoundException, ShowNotFoundException,ShowAlreadyExistsException {
        //is show already present in theater throw error
        Theater theater = getTheaterByIdHelper(addShowForTheaterRequest.getTheaterId());
        if(theater.getTheaterShows().stream().anyMatch(show -> show.getShowId().equals(addShowForTheaterRequest.getTheaterShowId()))){
            throw new ShowAlreadyExistsException("Show already exists for the given theater");
        }
        Show show = utilService.getShowById(addShowForTheaterRequest.getTheaterShowId());
        theater.getTheaterShows().add(show);
        theaterRepository.save(theater);
    }

    public void deleteTheaterShow(DeleteShowForTheaterRequestDTO deleteShowForTheaterRequest) throws TheaterNotFoundException , ShowNotFoundException {
        Theater theater = getTheaterByIdHelper(deleteShowForTheaterRequest.getTheaterId());
        Show show = theater.getTheaterShows().stream().filter(show1 -> show1.getShowId().equals(deleteShowForTheaterRequest.getTheaterShowId())).findFirst().orElseThrow(() -> new ShowNotFoundException("Show: " + deleteShowForTheaterRequest.getTheaterShowId() + " not found for theater: " + deleteShowForTheaterRequest.getTheaterId()));
        theater.getTheaterShows().remove(show);
        theaterRepository.save(theater);
    }
}
