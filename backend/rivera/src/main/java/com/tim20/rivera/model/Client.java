package com.tim20.rivera.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Client extends Person {
    private Integer numberOfPenalties;
    private Integer numberOfPoints;
    @OneToMany
    private List<Rentable> subscribed;
    @OneToMany
    private List<Reservation> reservations;
    @OneToMany
    private List<ClientCategoryCalendar> categories;
}
