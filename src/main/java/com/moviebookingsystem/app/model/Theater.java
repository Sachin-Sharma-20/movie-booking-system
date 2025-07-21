package com.moviebookingsystem.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Integer theaterId;

    @Column(nullable = false)
    private String theaterName;

    @OneToMany(mappedBy = "showTheater", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"showTheater"})
    private List<Show> theaterShows = new ArrayList<>();

}
