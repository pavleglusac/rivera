package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class ReservationReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_id")
    Reservation reservation;
    ReservationReportType reservationReportType;
    Boolean sanction;
    Boolean showedUp;
    String text;
    Boolean resolved;
    LocalDateTime posted;
    @ManyToOne
    @JoinColumn(name = "client_id")
    Client client;

    @Version
    private int version;
}
