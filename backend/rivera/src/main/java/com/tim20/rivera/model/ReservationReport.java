package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Data
@NoArgsConstructor
public class ReservationReport {
    @Id
    Integer id;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    Reservation reservation;

    ReservationReportType reservationReportType;

    Boolean sanction;

    Boolean showedUp;

    String text;




}
