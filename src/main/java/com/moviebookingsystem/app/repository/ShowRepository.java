package com.moviebookingsystem.app.repository;

import com.moviebookingsystem.app.model.Show;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {
    boolean existsByShowTheater_TheaterIdAndShowMovie_MovieId(Integer showTheaterId, Integer showMovieId);

}