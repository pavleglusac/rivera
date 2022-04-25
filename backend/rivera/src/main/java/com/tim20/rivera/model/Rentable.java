package com.tim20.rivera.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonIgnoreProperties("rentable")
public class Rentable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;
    private String name;
    private String description;
    private String address;
    private String city;
    private String country;
    @ElementCollection
    private List<String> pictures;
    private String profilePicture;
    @ElementCollection
    private List<String> rulesOfConduct;
    @ElementCollection
    private List<String> additionalServices;
    @ManyToMany
    private List<Tag> tags;
    private Double averageScore;
    @OneToMany(mappedBy = "rentable", cascade = CascadeType.ALL)
    private List<Review> reviews;
    @OneToMany(mappedBy = "rentable", cascade = CascadeType.PERSIST)
    private List<Discount> discounts;
    @OneToMany(mappedBy = "rentable", cascade = CascadeType.ALL)
    private List<Pricelist> pricelists;
    @OneToOne
    private Pricelist currentPricelist;
    @OneToMany(mappedBy = "rentable", cascade = CascadeType.ALL)
    private List<Calendar> calendars;
    @OneToMany(mappedBy = "rentable", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

}
