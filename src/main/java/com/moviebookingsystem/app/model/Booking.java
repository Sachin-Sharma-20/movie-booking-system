package com.moviebookingsystem.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingId;

    @ManyToOne()
    @JoinColumn(name = "user_id",nullable = false)
    @JsonIgnoreProperties({"userBookings"})
    private User bookingUser;

    @ManyToOne()
    @JoinColumn(name = "show_id",nullable = false)
    @JsonIgnoreProperties({"showBookings"})
    @JsonIgnore
    private Show bookingShow;

    @OneToOne()
    @JoinColumn(name = "seat_id",nullable = false)
    private Seat bookingSeat;
}
