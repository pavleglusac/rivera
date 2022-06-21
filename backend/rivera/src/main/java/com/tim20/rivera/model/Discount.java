package com.tim20.rivera.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties("discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Double price;
    private Integer capacity;
    private boolean reserved;

    @ManyToMany
    private List<Tag> tags;
    @ElementCollection
    private List<String> additionalServices;

    @ManyToOne
    @JoinColumn(name = "rentable_id")
    private Rentable rentable;
}