package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double price;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "rentable_id")
    private Rentable rentable;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Double ownerIncomePercentage;
    private Boolean cancelled;
    @ElementCollection
    private List<String> additionalServices;

    public static Integer pointsPerReservation;
    public static Integer ownerPoints;

    @OneToOne(mappedBy = "reservation")
    ReservationReport reservationReport;
}