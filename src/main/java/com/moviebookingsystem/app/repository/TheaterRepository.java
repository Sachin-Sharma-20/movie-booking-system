package com.moviebookingsystem.app.repository;

import com.moviebookingsystem.app.model.Theater;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Integer> {

    List<Theater> findByTheaterName(String theaterName);

    boolean existsByTheaterName(String theaterName);
}