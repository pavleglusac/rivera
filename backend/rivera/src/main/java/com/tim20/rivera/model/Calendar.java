package com.tim20.rivera.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties("calendars")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany
    private List<AvailabilityPattern> patternsOfAvailability;

    @ManyToOne
    @JoinColumn(name = "rentable_id")
    private Rentable rentable;


}
