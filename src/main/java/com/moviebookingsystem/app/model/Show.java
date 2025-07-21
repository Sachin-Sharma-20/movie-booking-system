package com.moviebookingsystem.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "shows")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer showId;

    @ManyToOne()
    @JoinColumn(name = "theater_id",nullable = false)
    @JsonIgnoreProperties({"theaterShows"})
    private Theater showTheater;

    @ManyToOne()
    @JoinColumn(name = "movie_id",nullable = false)
    private Movie showMovie;

    @OneToMany(mappedBy = "bookingShow",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties({"bookingShow"})
    private List<Booking> showBookings = new ArrayList<>();

    private Integer showSeatCount = 11;

    @OneToMany(mappedBy = "seatShow",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Seat> showSeats = new ArrayList<>();

}
