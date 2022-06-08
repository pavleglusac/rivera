package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    LocalDateTime posted;
    @ManyToOne
    @JoinColumn(name = "client_id")
    Client client;
}
