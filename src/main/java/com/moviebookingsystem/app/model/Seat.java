package com.moviebookingsystem.app.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moviebookingsystem.app.constants.SeatStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicUpdate
public class Seat {
    @Id
    @Column(nullable = false)
    private String seatId;

    @Column(nullable = false)
    private Integer seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    @ManyToOne()
    @JoinColumn(name = "show_id", nullable = false)
    @JsonIgnore
    private Show seatShow;

    @PrePersist
    public void setDefaults() {
        if(seatId==null && seatShow!=null){
            this.seatId = "seat_" + seatNumber + "_show_" + seatShow.getShowId();
        }
        if(seatStatus==null){
            seatStatus = SeatStatus.UNLOCKED;
        }
    }
}
