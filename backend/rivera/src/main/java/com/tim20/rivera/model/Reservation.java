package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double price;
    @ManyToOne
    private Client client;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Boolean cancelled;

    public static Integer pointsPerReservation;
    public static Integer ownerPoints;
}
