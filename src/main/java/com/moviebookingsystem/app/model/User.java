package com.moviebookingsystem.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.moviebookingsystem.app.constants.Role;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(unique = true,nullable = false)
    private String username;

    private String firstname;
    private String lastname;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    private Role role = Role.USER;

    @OneToMany(mappedBy = "bookingUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"bookingUser"})
    private List<Booking> userBookings;

}
