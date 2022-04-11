package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Pricelist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cancellationTerms;
    private Double pricePerHour;
    private Double pricePerDay;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}