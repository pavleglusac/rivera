package com.tim20.rivera.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@JsonIgnoreProperties("client")
public class Client extends Person {
    private Integer numberOfPenalties;
    private Integer numberOfPoints;
    private Boolean subscribed;
    @OneToMany(mappedBy = "client")
    private List<Reservation> reservations;
    @OneToMany
    private List<ClientCategoryCalendar> categories;
    @OneToMany(mappedBy = "client")
    private List<Review> reviews;
}
