package com.tim20.rivera.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties("client")
public class Client extends Person {
    private Integer numberOfPenalties;
    private Integer numberOfPoints;
    @OneToMany
    private List<Rentable> subscribed;
    @OneToMany(mappedBy = "client")
    private List<Reservation> reservations;
    @OneToMany(mappedBy = "client")
    private List<Review> reviews;
    private AccountStatus status;

    @Override
    public String toString() {
        return "Client{" +
                "numberOfPenalties=" + numberOfPenalties +
                ", numberOfPoints=" + numberOfPoints +
                '}';
    }
}
