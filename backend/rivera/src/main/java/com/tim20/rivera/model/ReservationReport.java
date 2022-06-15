package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class ReservationReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    Reservation reservation;

    ReservationReportType reservationReportType;

    Boolean sanction;

    Boolean showedUp;

    String text;

    Boolean resolved;


}
